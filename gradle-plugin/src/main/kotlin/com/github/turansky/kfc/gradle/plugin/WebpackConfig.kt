package com.github.turansky.kfc.gradle.plugin

import java.io.File

internal fun defaultOutputConfiguration(): String =
    // language=JavaScript
    """
        config.output = config.output || {}
        config.output.libraryTarget = 'umd'
        delete config.output.library
    """.trimIndent()

internal fun outputConfiguration(path: String): String =
    // language=JavaScript
    """
        config.output = config.output || {}
        config.output.libraryTarget = 'umd'
        config.output.libraryExport = ${libraryExport(path)}
        delete config.output.library
    """.trimIndent()

internal fun outputConfiguration(outputs: List<WebpackOutput>): String {
    val configs = outputs
        .joinToString(",\n\n")
        { outputConfiguration(it) }

    // language=JavaScript
    return """
        // multi output
        
        config.output = config.output || {}
        delete config.output.library

        config = [
          $configs
        ]
    """.trimIndent()
}

private fun outputConfiguration(output: WebpackOutput): String =
    // language=JavaScript
    """
          Object.assign({}, config, {
            name: ${output.name}
            output: {
              filename: "[name].js",
              libraryExport: ${libraryExport(output.root)}
            }
          })
    """.trimIndent()

internal fun entryConfiguration(
    output: Output? = null,
    entry: File
): String {
    val entryId = output?.id ?: "main"

    // language=JavaScript
    return """
        if ('$entryId' === 'main' && config.mode !== 'production') {
            return
        }

        config.entry['$entryId'] = ${entry.toPathString()}
    """.trimIndent()
}

private fun libraryExport(path: String): String =
    path.split(".")
        .map { "'$it'" }
        .joinToString(", ")
        .let { "[ $it ]" }
