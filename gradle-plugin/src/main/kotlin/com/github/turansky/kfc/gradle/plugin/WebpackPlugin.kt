package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

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

    val dce = withType<KotlinJsDce>()
        .singleOrNull()
        ?: return

    val devMode = dce.dceOptions.devMode
    val outputDirectory = dce.jsPackageDir("kotlin-dce-2")
    val kotlinFilesOnly = dce.kotlinFilesOnly
    val keep = dce.keep.toList()

    val taskMap = mutableMapOf(outputs.first() to dce)
        .apply {
            outputs.asSequence()
                .drop(1)
                .forEach {
                    val task = register<KotlinJsDce>("${dce.name}__${it.name}").get()
                    dce.dependsOn(task)
                    put(it, task)
                }
        }.toMap()

    for ((output, task) in taskMap) {
        task.destinationDir = outputDirectory.resolve(output.name)

        task.source = dce.source
        task.classpath = dce.classpath
        task.sourceCompatibility = dce.sourceCompatibility
        task.targetCompatibility = dce.targetCompatibility

        task.dceOptions.devMode = devMode
        task.dceOptions.outputDirectory = task.destinationDir.absolutePath
        task.kotlinFilesOnly = kotlinFilesOnly

        task.keep.clear()
        task.keep.addAll(keep)
        task.keepPath(output.root)
    }

    configureEach<PatchWebpackConfig> {
        inlinePatch(outputConfiguration(outputs, outputDirectory, project.jsProjectId))
    }
}
