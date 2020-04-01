package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.Output.LOCAL_SERVER
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

class DevServerPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.withType<KotlinJsPluginWrapper> {
            plugins.apply(WebpackPlugin::class)

            val extension = extensions.create<DevServerExtension>("localServer")
            val generateExportAlias = tasks.register<GenerateExportAlias>("generateExportAlias")

            tasks {
                useModularJsTarget()

                configureEach<KotlinJsDce> {
                    enabled = false
                }

                configureEach<KotlinWebpack> {
                    if (name !in DEVELOPMENT_RUN_TASKS) {
                        enabled = false
                    }

                    outputFileName = LOCAL_SERVER.fileName
                    sourceMaps = false

                    dependsOn(generateExportAlias)
                }

                configureEach<PatchWebpackConfig> {
                    patch(
                        "00__init__00",
                        // language=JavaScript
                        """
                        |const mainEntry = config.entry.main[0]
                        |delete config.entry.main
                        |
                        |config.entry['${LOCAL_SERVER.id}'] = mainEntry
                        |
                        |config.output = config.output || {}
                        |config.output.filename = '[name].js'
                        |config.output.libraryTarget = 'umd'
                        |delete config.output.library
                        """.trimMargin()
                    )

                    relatedProjects()
                        .mapNotNull { it.tasks.findGenerateWebComponent() }
                        .onEach { dependsOn(it) }
                        .takeIf { it.isNotEmpty() }
                        ?.also { patch("entries", it.entryConfiguration()) }
                }
            }

            afterEvaluate {
                val localServerRoot = extension.root
                    ?: return@afterEvaluate

                generateExportAlias {
                    export = localServerRoot
                }

                tasks.configureEach<PatchWebpackConfig> {
                    val entry = entryConfiguration(
                        output = LOCAL_SERVER,
                        entry = generateExportAlias.get().entry
                    )
                    patch("entry", entry)
                }
            }

            afterEvaluate {
                val proxies = extension.proxies
                    .ifEmpty { return@afterEvaluate }
                    .toList()

                tasks.configureEach<PatchWebpackConfig> {
                    for (proxy in proxies) {
                        val source = requireNotNull(proxy.source) {
                            "Proxy source project is undefined"
                        }

                        val port = proxy.port.also {
                            check(it > 0) {
                                "Invalid proxy port: '$it'"
                            }
                        }

                        patch(
                            "application-proxy",
                            devServerConfiguration(
                                project = source,
                                port = port
                            )
                        )
                    }
                }
            }
        }
    }
}

