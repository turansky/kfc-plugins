package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.register
import java.io.File

private val GENERATE_WEB_COMPONENT = "generateWebComponent"

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

internal fun TaskContainer.registerGenerateWebComponent(): TaskProvider<GenerateWebComponent> =
    register<GenerateWebComponent>(GENERATE_WEB_COMPONENT)

internal fun TaskContainer.findGenerateWebComponent(): GenerateWebComponent? =
    findByName(GENERATE_WEB_COMPONENT) as? GenerateWebComponent

internal fun List<GenerateWebComponent>.entryConfiguration() =
    joinToString("\n") {
        "config.entry['${it.project.name}'] = ${it.entry.toPathString()}"
    }
