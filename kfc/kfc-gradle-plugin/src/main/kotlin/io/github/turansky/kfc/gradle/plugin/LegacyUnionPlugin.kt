package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import java.io.File

private val UNION = Regex("""\(/\*union\*/\{(\w.+?)}/\*union\*/\)\.([\w\d_]+)""")

class LegacyUnionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.configureEach<Kotlin2JsCompile> {
            doLast {
                val file = File(kotlinOptions.outputFile!!)
                applyUnionPatch(file)
            }
        }
    }
}

private fun applyUnionPatch(file: File) {
    val newContent = file.readLines()
        .joinToString("\n") {
            it.replace(UNION, ::unionCallToValue)
        }

    file.writeText(newContent)
}

private fun unionCallToValue(result: MatchResult): String {
    val (enum, id) = result.destructured
    var start = "$id: '"
    if (!enum.startsWith(start))
        start = " $start"

    val value = enum
        .substringAfter(start, "")
        .substringBefore("'", "")

    return "'$value'"
}
