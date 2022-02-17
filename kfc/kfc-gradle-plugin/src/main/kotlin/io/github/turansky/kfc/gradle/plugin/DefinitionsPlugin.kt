package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

private val REDUNDANT_PACKAGES = listOf(
    "kotlin.js",

    "org.w3c.dom.url",
    "org.w3c.dom"
)

// https://youtrack.jetbrains.com/issue/KT-51205
private val KT_51205 = Regex(""" any/\* ([^*/]*) \*/""")

internal class DefinitionsPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.configureEach<Kotlin2JsCompile> {
            doLast {
                val outputFile = outputFileProperty.get()
                val definitionFile = outputFile.parentFile
                    .resolve(outputFile.name.substringBeforeLast(".") + ".d.ts")

                if (definitionFile.exists()) {
                    val content = definitionFile.readText()
                    val newContent = REDUNDANT_PACKAGES
                        .fold(content) { acc, p ->
                            acc.replace("$p.", "")
                        }
                        .replace(KT_51205, " $1")

                    if (newContent != content) {
                        definitionFile.writeText(newContent)
                    }
                }
            }
        }
    }
}
