package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.register

class SingleCachePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val bundler = getBundler()

        tasks.link(bundler.productionTask, bundler.developmentTask)
        tasks.link(bundler.developmentTask, bundler.productionTask)
    }

    private fun TaskContainer.link(
        taskName: String,
        relatedTaskName: String,
    ) {
        val relatedTaskOutputDirectory = named(relatedTaskName).get()
            .outputDirectory()
            .get()

        val singleCacheTask = "${taskName}SingleCache"
        register<Delete>(singleCacheTask) {
            delete(relatedTaskOutputDirectory)
        }

        named(taskName) {
            dependsOn(singleCacheTask)
        }
    }
}

private fun Task.outputDirectory(): DirectoryProperty =
    property("outputDirectory") as DirectoryProperty
