package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

class DevServerPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinJsPlugin(run = true)

        tasks {
            disable<KotlinJsDce> {
                name !in DEVELOPMENT_DCE_TASKS
            }

            disable<KotlinWebpack> {
                name !in DEVELOPMENT_RUN_TASKS
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

                eachApplicationDependency {
                    entry(it)
                }
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
