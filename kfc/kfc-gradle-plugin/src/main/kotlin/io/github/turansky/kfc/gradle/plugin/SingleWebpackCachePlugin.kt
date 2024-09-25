package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import javax.inject.Inject

class SingleWebpackCachePlugin
@Inject constructor(
    private val fs: FileSystemOperations,
) : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.named<KotlinWebpack>(Webpack.productionTask) {
            outputDirectory.set(getProductionDistDirectory())
        }

        tasks.named<KotlinWebpack>(Webpack.developmentTask) {
            outputDirectory.set(getDevelopmentDistDirectory())
        }

        tasks.link(Webpack.productionTask, Webpack.developmentTask)
        tasks.link(Webpack.developmentTask, Webpack.productionTask)
    }

    private fun TaskContainer.link(
        taskName: String,
        relatedTaskName: String,
    ) {
        val relatedTaskOutputDirectory = named<KotlinWebpack>(relatedTaskName).get().outputDirectory

        named<KotlinWebpack>(taskName) {
            doFirst {
                fs.delete { delete(relatedTaskOutputDirectory) }
            }
        }
    }
}
