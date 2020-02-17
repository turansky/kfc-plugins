package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

internal open class WebpackConfigTask : DefaultTask() {
    @get:Input
    val patches: MutableMap<String, String> = mutableMapOf()

    @get:OutputDirectory
    val configDirectory: File
        get() = project.projectDir.resolve("webpack.config.d")

    fun patch(name: String, body: String) {
        patches.put(name, body)
    }

    @TaskAction
    private fun generatePatches() {
        for ((name, body) in patches) {
            generate(name, body)
        }
    }

    @TaskAction
    private fun generateResources() {
        val resources = project.relatedResources()
        if (resources.isEmpty()) {
            return
        }

        val rootDir = project.rootDir
        val paths = resources
            .map { it.toRelativeString(rootDir) }
            .map { "path.resolve(__dirname, '../../../../$it')" }
            // TODO: realize valid stringify
            .map { it.replace("\\", "/") }
            .joinToString(",\n")

        // language=JavaScript
        val body = """
            const path = require('path')
            
            config.resolve.modules.unshift(
                $paths
            )
        """.trimIndent()

        generate("resources", body)
    }

    private fun generate(name: String, body: String) {
        configDirectory
            .also { it.mkdirs() }
            .resolve("$name.js")
            .writeText(patch(body))
    }
}

private fun patch(body: String): String =
    """
        ;(function () {
        $body
        })()
    """.trimIndent()
