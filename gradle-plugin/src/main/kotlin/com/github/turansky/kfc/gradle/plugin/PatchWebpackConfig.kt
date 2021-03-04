package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

open class PatchWebpackConfig : DefaultTask() {
    init {
        group = DEFAULT_TASK_GROUP
    }

    @get:Input
    val patches: MutableMap<String, String> = mutableMapOf()

    @get:Input
    val unsafePatches: MutableList<String> = mutableListOf()

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

    fun unsafePatch(body: String) {
        unsafePatches += body
    }

    @TaskAction
    private fun generatePatches() {
        if (patches.isEmpty() && unsafePatches.isEmpty()) {
            return
        }

        val content = patches
            .asSequence()
            .sortedBy { it.key }
            .map { (name, body) -> createPatch(name, body) }
            .plus(unsafePatches)
            .joinToString("\n\n")

        configDirectory
            .also { it.mkdirs() }
            .resolve("patch.js")
            .writeText(content)
    }
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
