package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

internal open class WebpackConfigTask : DefaultTask() {
    @get:Input
    var resources: List<File>? = null

    @TaskAction
    private fun generateResources() {
        val resources = resources
            ?.takeIf { it.isNotEmpty() }
            ?: return

        val paths = resources
            .map { "\"${it.absolutePath}\"" }
            .joinToString(",\n")

        val body = """
              config.resolve.modules.unshift(
                $paths
              )
        """.trimIndent()

        generate("resources", body)
    }

    private fun generate(name: String, body: String) {
        project.projectDir
            .resolve("webpack.config.d")
            .also { it.mkdirs() }
            .resolve("$name.generated.js")
            .writeText(patch(body))
    }
}

private fun patch(body: String): String =
    """
        ;(function () {
        $body
        })()
    """.trimIndent()
