package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce
import java.io.File

open class PatchWebpackConfig : DefaultTask() {
    init {
        group = DEFAULT_TASK_GROUP
    }

    @get:Input
    val patches: MutableMap<String, String> = mutableMapOf()

    @get:OutputDirectory
    val configDirectory: File
        get() = project.projectDir.resolve("webpack.config.d")

    fun patch(body: String) {
        val index = patches.size + 1
        patch("generated_$index", body)
    }

    fun patch(name: String, body: String) {
        if (patches.containsKey(name)) {
            patch(name + "_", body)
        } else {
            patches[name] = body
        }
    }

    fun entry(
        name: String,
        file: File,
    ) {
        patch("config.entry['$name'] = '${file.absolutePath}'")
    }

    fun entry(
        name: String,
        moduleName: String = name
    ) {
        if (project.jsIrCompiler)
            TODO("Support in IR?")

        entry(name, dceFile(moduleName))
    }

    fun entry(
        project: Project
    ) {
        if (project.jsIrCompiler) {
            // TODO: use task for path calculation
            val fileName = project.jsModuleName + ".js"
            entry(project.jsOutputName, project.jsPackageDir(fileName))
        } else {
            entry(project.jsOutputName, project.jsModuleName)
        }
    }

    fun proxy(target: String) {
        // language=JavaScript
        patch(
            """
              config.devServer.proxy = {
                context: () => true,
                target: '$target',
              }
            """.trimIndent()
        )
    }

    @TaskAction
    private fun generatePatches() {
        if (patches.isEmpty()) {
            return
        }

        val content = patches
            .asSequence()
            .sortedBy { it.key }
            .map { (name, body) -> createPatch(name, body) }
            .joinToString("\n\n")

        configDirectory
            .also { it.mkdirs() }
            .resolve("patch.js")
            .writeText(content)
    }

    @Suppress("UnstableApiUsage")
    private fun dceFile(name: String): File =
        project.tasks
            .getByName<KotlinJsDce>("processDceDevKotlinJs")
            .destinationDirectory
            .file("$name.js")
            .get()
            .asFile
}

@Suppress("JSUnnecessarySemicolon")
private fun createPatch(
    name: String,
    body: String
): String =
    // language=JavaScript
    """
        |// $name
        |;(function (config) {
        |$body
        |})(config)
    """.trimMargin()
