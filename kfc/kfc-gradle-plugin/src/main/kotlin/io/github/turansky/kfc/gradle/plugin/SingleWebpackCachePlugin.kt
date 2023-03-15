package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

class SingleWebpackCachePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.link(Webpack.PRODUCTION_TASK, Webpack.DEVELOPMENT_TASK)
        tasks.link(Webpack.DEVELOPMENT_TASK, Webpack.PRODUCTION_TASK)
    }

    private fun TaskContainer.link(
        taskName: String,
        relatedTaskName: String,
    ) {
        named<KotlinWebpack>(taskName) {
            doFirst {
                val relatedTask = named<KotlinWebpack>(relatedTaskName).get()
                project.delete(relatedTask.destinationDirectory)
            }
        }
    }
}
