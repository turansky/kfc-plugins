package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

class SingleWebpackCachePlugin : Plugin<Project> {
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
        named<KotlinWebpack>(taskName) {
            doFirst {
                val relatedTask = named<KotlinWebpack>(relatedTaskName).get()
                project.delete(relatedTask.outputDirectory)
            }
        }
    }
}
