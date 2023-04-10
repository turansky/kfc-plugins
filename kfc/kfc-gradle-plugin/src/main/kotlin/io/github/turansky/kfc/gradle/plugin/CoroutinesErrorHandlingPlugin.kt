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
            replace(OLD_COROUTINES_ERROR_HANDLING, NEW_COROUTINES_ERROR_HANDLING)
        }
    }
}
