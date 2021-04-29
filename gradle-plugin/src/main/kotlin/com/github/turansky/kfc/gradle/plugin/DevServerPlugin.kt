package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.Output.DEV_SERVER
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

// language=JavaScript
private const val PORT_PATCH: String = """
  const devServer = config.devServer 
  devServer.port = 9000
"""

// language=JavaScript
private const val KT_46162_PATCH: String = """
  const devServer = config.devServer

  devServer.static = devServer.contentBase

  delete devServer.contentBase
  delete devServer.overlay
  delete devServer.noInfo
  delete devServer.lazy
  delete devServer.inline
"""

// WA for https://youtrack.jetbrains.com/issue/KT-46162
private class DevServerRootPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.withType<NodeJsRootPlugin> {
            the<NodeJsRootExtension>().versions.apply {
                webpackDevServer.version = "4.0.0-beta.2"
            }
        }
    }
}

class DevServerPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        rootProject.plugins.apply(DevServerRootPlugin::class)

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

                patch("00__KT_46162__00", KT_46162_PATCH)
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
    action: (Project) -> Unit
) {
    project.relatedRuntimeProjects()
        .filter { it.plugins.hasPlugin(ApplicationPlugin::class) }
        .forEach(action)
}

