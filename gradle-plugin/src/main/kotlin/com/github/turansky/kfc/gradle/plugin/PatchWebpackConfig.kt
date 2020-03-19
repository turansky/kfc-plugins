package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

open class PatchWebpackConfig : DefaultTask() {
    @get:Input
    val patches: MutableMap<String, String> = mutableMapOf()

    @get:Input
    val inlinePatches: MutableList<String> = mutableListOf()

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

    internal fun inlinePatch(body: String) {
        inlinePatches += body
    }

    @TaskAction
    private fun generatePatches() {
        val content = patches
            .asSequence()
            .sortedBy { it.key }
            .map { (name, body) -> createPatch(name, body) }
            .plus(inlinePatches)
            .joinToString("\n\n")

        configDirectory
            .also { it.mkdirs() }
            .resolve("patch.js")
            .writeText(content)
    }
}

private fun createPatch(
    name: String,
    body: String
): String =
    """
        |// $name
        |;(function (config) {
        |$body
        |})(config)
    """.trimMargin()
