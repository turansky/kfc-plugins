package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register

class SingleViteCachePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.link(Vite.productionTask, Vite.developmentTask)
        tasks.link(Vite.developmentTask, Vite.productionTask)
    }

    private fun TaskContainer.link(
        taskName: String,
        relatedTaskName: String,
    ) {
        val relatedTaskOutputDirectory = named<KotlinViteTask>(relatedTaskName).get().outputDirectory.get()

        val singleCacheTask = "${taskName}SingleCache"
        register<Delete>(singleCacheTask) {
            delete(relatedTaskOutputDirectory)
        }

        named<KotlinViteTask>(taskName) {
            dependsOn(singleCacheTask)
        }
    }
}
