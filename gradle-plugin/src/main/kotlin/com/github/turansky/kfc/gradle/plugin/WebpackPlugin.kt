package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper

class WebpackPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        // TODO: Remove after fix
        //  https://youtrack.jetbrains.com/issue/KT-37587
        projectDir.resolve("webpack.config.d").mkdir()

        val extension = extensions.create<WebpackExtension>("webpack")

        plugins.withType<KotlinJsPluginWrapper> {
            tasks {
                val patchWebpackConfig = register<PatchWebpackConfig>("patchWebpackConfig") {
                    addResourceModules()
                }

                named<Delete>("clean") {
                    delete(patchWebpackConfig)
                }

                configureEach<KotlinJsCompile> {
                    dependsOn(patchWebpackConfig)
                }
            }
        }

        afterEvaluate {
            tasks.configureOutputs(extension.outputs.toList())
        }
    }
}

private fun PatchWebpackConfig.addResourceModules() {
    val resources = project.relatedResources()
    if (resources.isEmpty()) {
        return
    }

    val paths = resources.joinToString(",\n") {
        it.toPathString()
    }

    // language=JavaScript
    val body = """
            |config.resolve.modules.unshift(
            |    $paths
            |)
        """.trimMargin()

    patch("resources", body)
}

private fun TaskContainer.configureOutputs(
    outputs: List<WebpackOutput>
) {
    if (outputs.isEmpty()) {
        return
    }

    configureEach<KotlinJsDce> {
        for (output in outputs) {
            keepPath(output.root)
        }
    }

    configureEach<PatchWebpackConfig> {
        inlinePatch(outputConfiguration(outputs))
    }
}
