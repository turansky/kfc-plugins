package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

internal open class WebpackConfigTask : DefaultTask() {
    @get:Input
    var resources: List<File>? = null

    @TaskAction
    fun generate() {
        val resources = resources
            ?.takeIf { it.isNotEmpty() }
            ?: return

        project.projectDir
            .resolve("webpack.config.d")
            .also { it.mkdirs() }
            .resolve("resources.generated.js")
            .writeText(resources.toString())
    }
}
