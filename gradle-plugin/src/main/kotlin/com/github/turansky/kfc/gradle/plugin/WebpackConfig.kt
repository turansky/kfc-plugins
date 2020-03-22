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

internal fun outputConfiguration(
    outputs: List<WebpackOutput>,
    getEntry: (WebpackOutput) -> File
): String {
    val configs = outputs
        .joinToString(",\n\n")
        { outputConfiguration(it, getEntry(it)) }

    // language=JavaScript
    return """
        // multi output
        if (config.mode == 'production') {
          const plugins = config.plugins
          config = [
            $configs
          ]
          
          // WA temp progress plugin fix
          config.plugins = plugins
        }
    """.trimIndent()
}

private fun outputConfiguration(
    output: WebpackOutput,
    entry: File
): String {
    val buildDir = entry.parentFile

    // language=JavaScript
    return """
          Object.assign({}, config, {
            name: '${output.name}',
            entry: {
              main: ${entry.toPathString()}
            },
            resolve: {
              modules: [
                  ${buildDir.toPathString()},
                  'node_modules'
              ]
            },
            output: {
              path: config.output.path,
              filename: '${output.name}.js',
              libraryTarget: 'umd',
              libraryExport: ${libraryExport(output.root)}
            }
          })
    """.trimIndent()
}

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
