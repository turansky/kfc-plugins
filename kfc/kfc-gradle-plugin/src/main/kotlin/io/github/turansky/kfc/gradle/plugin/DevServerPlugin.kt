package io.github.turansky.kfc.gradle.plugin

import io.github.turansky.kfc.gradle.plugin.Output.DEV_SERVER
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

// language=JavaScript
private const val PORT_PATCH: String = """
  const devServer = config.devServer 
  devServer.port = 9000
"""

class DevServerPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinJsPlugin(run = true)

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

                dependsOn(generateExportAlias)
            }

            if (jsIrCompiler) {
                named(COMPILE_DEVELOPMENT) {
                    eachApplicationDependency {
                        dependsOn(it.tasks.named(COMPILE_DEVELOPMENT))
                    }
                }
            }

            configureEach<PatchWebpackConfig> {
                val fileName = outputPath("[name].js")

                patch(
                    "00__init__00",
                    // language=JavaScript
                    """
                        delete config.entry.main

                        config.output.filename = '$fileName'
                    """.trimIndent()
                )

                patch("dev-server-port", PORT_PATCH)

                relatedProjects()
                    .mapNotNull { it.tasks.findGenerateExportProxy() }
                    .onEach { dependsOn(it) }
                    .takeIf { it.isNotEmpty() }
                    ?.also { patch("entries", it.entryConfiguration()) }

                eachApplicationDependency {
                    entry(it)
                }
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
    }
}

internal fun Task.eachApplicationDependency(
    action: (Project) -> Unit,
) {
    project.relatedRuntimeProjects()
        .filter { it.plugins.hasPlugin(ApplicationPlugin::class) }
        .forEach(action)
}

