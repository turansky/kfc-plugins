package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.named

class SingleViteCachePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.link(Vite.productionTask, Vite.developmentTask)
        tasks.link(Vite.developmentTask, Vite.productionTask)
    }

    private fun TaskContainer.link(
        taskName: String,
        relatedTaskName: String,
    ) {
        named<KotlinViteTask>(taskName) {
            doFirst {
                val relatedTask = named<KotlinViteTask>(relatedTaskName).get()
                project.delete(relatedTask.outputDirectory)
            }
        }
    }
}
