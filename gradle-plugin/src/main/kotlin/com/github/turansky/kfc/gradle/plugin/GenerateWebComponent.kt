package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.register
import java.io.File

private val GENERATE_WEB_COMPONENT = "generateWebComponent"

open class GenerateWebComponent : DefaultTask() {
    @get:Input
    var components: List<String> = emptyList()

    @get:OutputFile
    val entry: File
        get() = getEntry()

    private fun getEntry(createMode: Boolean = false): File =
        jsPackageDir("webcomponent")
            .also { if (createMode) it.mkdir() }
            .resolve("index.js")

    @TaskAction
    private fun generate() {
        val components = components.toList()
        check(components.isNotEmpty())
        getEntry(true).writeText(WebComponent.wrap(jsProjectId, components))
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
