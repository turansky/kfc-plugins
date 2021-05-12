// WA for https://github.com/Kotlin/kotlinx.serialization/issues/1449

package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

private const val PARAMETER_NAME = "local0"
private val VALUE_CLASS_RETURN = Regex("""return (\w+)_init\(bitMask0, $PARAMETER_NAME, null\);""")

internal class ValueClassSerializationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        if (jsIrCompiler)
            return@with

        plugins.withId(KotlinPlugin.SERIALIZATION) {
            tasks.withType<Kotlin2JsCompile> {
                doLast {
                    // IR check (both)
                    if (outputFile.isDirectory)
                        return@doLast

                    val content = outputFile.readText()
                    val newContent = applySerializationFixes(content)
                    if (newContent != content) {
                        outputFile.writeText(newContent)
                    }
                }
            }
        }
    }
}

private fun applySerializationFixes(
    source: String
): String {
    val classNames = VALUE_CLASS_RETURN.findAll(source)
        .map { it.groupValues[1] }
        .toSet()

    if (classNames.isEmpty())
        return source

    return source
}
