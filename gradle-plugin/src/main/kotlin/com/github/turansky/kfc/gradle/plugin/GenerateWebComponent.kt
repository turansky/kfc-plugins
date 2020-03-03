package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

open class GenerateWebComponent : DefaultTask() {
    @get:Input
    var component: WebComponent? = null

    @get:OutputFile
    val entry: File
        get() = getEntry()

    private fun getEntry(createMode: Boolean = false): File =
        project.jsPackageDir
            .resolve("webcomponent")
            .also { if (createMode) it.mkdir() }
            .resolve("index.js")

    @TaskAction
    private fun generate() {
        val component = checkNotNull(component)
        getEntry(true).writeText(component.toCode(project.jsProjectId))
    }
}
