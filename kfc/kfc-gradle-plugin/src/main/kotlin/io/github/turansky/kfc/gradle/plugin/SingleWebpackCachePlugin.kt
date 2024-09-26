package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskContainer
import org.gradle.internal.extensions.stdlib.uncheckedCast
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

class SingleWebpackCachePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.link(Webpack.productionTask, Webpack.developmentTask)
        tasks.link(Webpack.developmentTask, Webpack.productionTask)
    }

    private fun TaskContainer.link(
        taskName: String,
        relatedTaskName: String,
    ) {
        val relatedTaskOutputDirectory = named(relatedTaskName).get()
            .property("outputDirectory")
            ?.uncheckedCast<DirectoryProperty>()
            ?.get()

        val singleCacheTask = "${taskName}SingleCache"
        register<Delete>(singleCacheTask) {
            delete(relatedTaskOutputDirectory)
        }

        named<KotlinWebpack>(taskName) {
            dependsOn(singleCacheTask)
        }
    }
}
