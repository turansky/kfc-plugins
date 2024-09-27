package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.register

abstract class SingleCacheBundlerPlugin(
    private val bundler: Bundler,
    objects: ObjectFactory,
) : Plugin<Project> {
    @InputDirectory
    var productionOutputDir = objects.directoryProperty()

    @InputDirectory
    var developmentOutputDir = objects.directoryProperty()

    override fun apply(target: Project): Unit = with(target) {
        tasks.link(bundler.productionTask, developmentOutputDir)
        tasks.link(bundler.developmentTask, productionOutputDir)
    }

    private fun TaskContainer.link(
        taskName: String,
        relatedDirectory: DirectoryProperty,
    ) {
        val singleCacheTask = "${taskName}Clean"
        register<Delete>(singleCacheTask) {
            delete(relatedDirectory)
        }

        named(taskName) {
            dependsOn(singleCacheTask)
        }
    }
}
