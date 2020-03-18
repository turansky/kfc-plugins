package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

open class PatchWebpackConfig : DefaultTask() {
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

    @TaskAction
    private fun generatePatches() {
        val content = getAllPatches()
            .asSequence()
            .sortedBy { it.key }
            .map { (name, body) -> createPatch(name, body) }
            .joinToString("\n\n")

        configDirectory
            .also { it.mkdirs() }
            .resolve("patch.js")
            .writeText(content)
    }

    private fun getAllPatches(): Map<String, String> {
        val resourcePatch = resourcePatch()
            ?: return patches.toMap()

        return patches.toMutableMap()
            .apply { put(resourcePatch.first, resourcePatch.second) }
            .toMap()
    }

    private fun resourcePatch(): Pair<String, String>? {
        val resources = project.relatedResources()
        if (resources.isEmpty()) {
            return null
        }

        val paths = resources.joinToString(",\n") {
            it.toPathString()
        }

        // language=JavaScript
        val body = """
            |config.resolve.modules.unshift(
            |    $paths
            |)
        """.trimMargin()

        return "resources" to body
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
