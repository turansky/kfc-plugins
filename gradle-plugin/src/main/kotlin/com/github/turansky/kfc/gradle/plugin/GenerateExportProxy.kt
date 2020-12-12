package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.register
import java.io.File

private val GENERATE_EXPORT_PROXY = "generateExportProxy"

open class GenerateExportProxy : DefaultTask() {
    @get:Input
    var classNames: List<String> = emptyList()

    @get:OutputFile
    val entry: File
        get() = getEntry()

    private fun getEntry(createMode: Boolean = false): File =
        jsPackageDir("export-proxy")
            .also { if (createMode) it.mkdir() }
            .resolve("index.js")

    @TaskAction
    private fun generate() {
        val classNames = classNames.toList()
        check(classNames.isNotEmpty())
        getEntry(true).writeText(proxy(jsProjectId, classNames))
    }
}

internal fun TaskContainer.registerGenerateExportProxy(): TaskProvider<GenerateExportProxy> =
    register<GenerateExportProxy>(GENERATE_EXPORT_PROXY)

internal fun TaskContainer.findGenerateExportProxy(): GenerateExportProxy? =
    findByName(GENERATE_EXPORT_PROXY) as? GenerateExportProxy

internal fun List<GenerateExportProxy>.entryConfiguration() =
    joinToString("\n") {
        "config.entry['${it.project.name}'] = ${it.entry.toPathString()}"
    }

private fun proxy(
    moduleName: String,
    components: List<String>
): String =
    "import * as source from '$moduleName'\n\n" +
            components.joinToString("\n") {
                val name = it.substringAfterLast(".")
                "export const $name = source.$it"
            }
