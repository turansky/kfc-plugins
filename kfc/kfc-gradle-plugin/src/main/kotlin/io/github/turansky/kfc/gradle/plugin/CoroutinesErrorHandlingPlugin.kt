// TODO: remove after issue fix
//  https://github.com/Kotlin/kotlinx.coroutines/issues/2407

package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

// language=JavaScript
private const val OLD_COROUTINES_ERROR_HANDLING: String = """
  function handleCoroutineExceptionImpl(context, exception) {
    console.error(exception);
  }
"""

// language=JavaScript
private const val NEW_COROUTINES_ERROR_HANDLING: String = """
  function handleCoroutineExceptionImpl(context, exception) {
    globalThis.dispatchEvent(new ErrorEvent('error', { error: exception }));
  }
"""

class CoroutinesErrorHandlingPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.configureEach<PatchWebpackConfig> {
            patch(
                name = "coroutine-error-handling",
                body = createReplacePatch(
                    StringReplacement(OLD_COROUTINES_ERROR_HANDLING, NEW_COROUTINES_ERROR_HANDLING),
                    StringReplacement(OLD_COROUTINES_ERROR_HANDLING.trimIndent(), NEW_COROUTINES_ERROR_HANDLING),
                )
            )
        }
    }
}

private data class StringReplacement(
    private val oldValue: String,
    private val newValue: String,
) {
    val search: String = multiline(escape(oldValue))
    val replace: String = multiline(newValue)
    val flags: String = listOfNotNull(
        "g",
        "m".takeIf { "\n" in oldValue },
    ).joinToString("")

    companion object {
        private fun multiline(
            source: String,
        ): String =
            source.replace("\n", """\n""")

        private const val SPECIAL_SYMBOLS = "(){}.,"

        private fun escape(source: String): String =
            SPECIAL_SYMBOLS.asSequence()
                .fold(source) { acc, char ->
                    acc.replace(char.toString(), """\\$char""")
                }
    }
}

private fun createReplacePatch(
    vararg replacements: StringReplacement,
): String {
    val replacementOptions = replacements.map { r ->
        """{ search: "${r.search}", replace: "${r.replace}", flags : "${r.flags}" },"""
    }.joinToString("\n                ")

    // language=JavaScript
    return """
        config.module.rules.push(
          {
            test: /\.js${'$'}/,
            loader: 'string-replace-loader',
            options: {
              multiple: [
                $replacementOptions
              ]
            }
          },
        )
    """.trimIndent()
}
