package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.Output.DEV_SERVER
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

class DevServerPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinJsPlugin(run = true)
        plugins.apply(WebpackPlugin::class)
        plugins.apply(WebComponentPlugin::class)

        val extension = extensions.create<DevServerExtension>("devServer")
        val generateExportAlias = tasks.register<GenerateExportAlias>("generateExportAlias")

        tasks {
            disable<KotlinJsDce> {
                name !in DEVELOPMENT_DCE_TASKS
            }

            disable<KotlinWebpack> {
                name !in DEVELOPMENT_RUN_TASKS
            }

            configureEach<KotlinWebpack> {
                outputFileName = DEV_SERVER.fileName
                sourceMaps = false

                dependsOn(generateExportAlias)
            }

            configureEach<PatchWebpackConfig> {
                patch(
                    "00__init__00",
                    // language=JavaScript
                    """
                        const mainEntry = config.entry.main[0]
                        delete config.entry.main
                        
                        config.entry['${DEV_SERVER.id}'] = mainEntry
                        
                        config.output = config.output || {}
                        config.output.filename = '[name].js'
                    """.trimIndent()
                )

                relatedProjects()
                    .mapNotNull { it.tasks.findGenerateExportProxy() }
                    .onEach { dependsOn(it) }
                    .takeIf { it.isNotEmpty() }
                    ?.also { patch("entries", it.entryConfiguration()) }
            }
        }

        afterEvaluate {
            val devServerRoot = extension.root
                ?: return@afterEvaluate

            generateExportAlias {
                export = devServerRoot
            }

            tasks.configureEach<PatchWebpackConfig> {
                val entry = entryConfiguration(
                    output = DEV_SERVER,
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
                    patch(
                        "application-proxy",
                        devServerConfiguration(
                            root = proxy.root ?: project.name,
                            source = project.tasks.getByPath(proxy.source),
                            port = proxy.port.also { it.validatePort() }
                        )
                    )
                }
            }
        }
    }
}

private fun Int.validatePort() {
    check(this > 0) {
        "Invalid port: '$this'"
    }
}

