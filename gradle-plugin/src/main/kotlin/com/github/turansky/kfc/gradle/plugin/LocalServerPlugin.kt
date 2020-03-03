package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.Output.LOCAL_SERVER
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

open class LocalServerExtension {
    var root: String? = null
}

class LocalServerPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.withType<KotlinJsPluginWrapper> {
            plugins.apply(WebpackPlugin::class)

            val extension = extensions.create<LocalServerExtension>("localServer")

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
                        |config.output.filename = "[name].js"
                        |config.output.libraryTarget = 'umd'
                        |delete config.output.library
                        """.trimMargin()
                    )

                    relatedProjects()
                        .filter { it.plugins.hasPlugin(WebComponentPlugin::class) }
                        .associate { it.name to it.jsPackageDir.resolve("webcomponent/index.js") }
                        .takeIf { it.isNotEmpty() }
                        ?.also {
                            val entries = it.asSequence()
                                .map { (name, entry) -> "config.entry['$name'] = ${entry.toPathString()}" }
                                .joinToString("\n")

                            patch("entries", entries)
                        }
                }
            }

            val generateExportAlias = tasks.register<GenerateExportAlias>("generateExportAlias")

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
        }
    }
}

