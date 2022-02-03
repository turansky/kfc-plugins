package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

open class GenerateExportAlias : DefaultTask() {
    init {
        group = DEFAULT_TASK_GROUP
    }

    @get:Input
    var export: String = ""

    @get:OutputFile
    val entry: File
        get() = getEntry()

    private fun getEntry(createMode: Boolean = false): File =
        jsPackageDir("kotlin-export-alias")
            .also { if (createMode) it.mkdir() }
            .resolve("index.js")

    @TaskAction
    private fun generate() {
        if (export.isEmpty()) return

        val content = alias(
            moduleName = jsModuleName,
            export = export
        )
        getEntry(true).writeText(content)
    }
}

// language=JavaScript
private fun alias(
    moduleName: String,
    export: String,
): String = """
        |const source = require('$moduleName')
        |
        |module.exports = source.$export
    """.trimMargin()
