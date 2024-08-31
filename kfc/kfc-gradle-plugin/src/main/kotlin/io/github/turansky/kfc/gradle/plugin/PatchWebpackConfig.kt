package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

class EnvVariable(
    val name: String,
    val value: String,
)

abstract class PatchWebpackConfig : DefaultTask() {

    @get:Input
    val patches: MutableMap<String, String> = mutableMapOf()

    @get:Input
    val envVariables: MutableList<Pair<String, String>> = mutableListOf()

    @get:OutputDirectory
    val configDirectory: File
        get() = project.projectDir.resolve("webpack.config.d")

    fun patch(
        body: String,
    ) {
        val index = patches.size + 1
        patch("generated_$index", body)
    }

    fun patch(
        source: File,
    ) {
        patch(source.readText())
    }

    fun patch(
        name: String,
        body: String,
    ) {
        if (patches.containsKey(name)) {
            patch(name + "_", body)
        } else {
            patches[name] = body
        }
    }

    fun patch(
        name: String,
        source: File,
    ) {
        patch(name, source.readText())
    }

    fun env(
        name: String,
        value: String,
    ) {
        envVariables.add(name to value)
    }

    @TaskAction
    private fun generatePatches() {
        val envPatch: String? = createEnvPatch(
            envVariables.map { (oldValue, newValue) ->
                EnvVariable(
                    name = oldValue,
                    value = newValue,
                )
            },
        )

        if (patches.isEmpty() && envPatch == null)
            return

        val content = patches
            .asSequence()
            .sortedBy { it.key }
            .map { (name, body) -> createPatch(name, body) }
            .let { if (envPatch != null) it + envPatch else it }
            .joinToString("\n\n")

        configDirectory
            .also { it.mkdirs() }
            .resolve("patch.js")
            .writeText(content)
    }
}

@Suppress("JSUnnecessarySemicolon")
private fun createEnvPatch(
    variables: List<EnvVariable>,
): String? {
    if (variables.isEmpty())
        return null

    val variableDeclarations = variables.map { v ->
        """'import.meta.env.VITE_${v.name}': JSON.stringify('${v.value}'),"""
    }.joinToString("\n                ")

    return createPatch(
        name = "env-variables",
        // language=JavaScript
        body = """
        const DefinePlugin = require('webpack').DefinePlugin

        config.plugins.push(
           new DefinePlugin({
               $variableDeclarations
           })
        )
        """.trimIndent()
    )
}

@Suppress("JSUnnecessarySemicolon")
private fun createPatch(
    name: String,
    body: String,
): String =
    // language=JavaScript
    """
        |// $name
        |;(function (config) {
        |$body
        |})(config)
    """.trimMargin()
