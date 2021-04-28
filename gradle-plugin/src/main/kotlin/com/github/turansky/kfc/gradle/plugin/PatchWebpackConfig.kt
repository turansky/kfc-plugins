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
        moduleName: String = name
    ) {
        patch("config.entry['$name'] = '${entryPath(moduleName)}'")
    }

    fun entry(
        project: Project
    ) {
        entry(project.jsOutputName, project.jsModuleName)
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
    private fun entryPath(name: String): String =
        project.tasks
            .getByName<KotlinJsDce>("processDceDevKotlinJs")
            .destinationDirectory
            .file("$name.js")
            .get()
            .asFile
            .absolutePath
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
