package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

private val REDUNDANT_PACKAGES = listOf(
    "kotlin.js",

    "org.w3c.dom.url",
    "org.w3c.dom"
)

internal class DefinitionsPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.configureEach<Kotlin2JsCompile> {
            doLast {
                val outputFile = outputFileProperty.get()
                val definitionFile = outputFile.parentFile
                    .resolve(outputFile.name.substringBeforeLast(".") + ".d.ts")

                if (definitionFile.exists()) {
                    val content = definitionFile.readText()
                    // https://youtrack.jetbrains.com/issue/KT-51205
                    val fixKT51205 = """: any\/\* ([^\*\/]*) \*\/""".toRegex()
                    val newContent = REDUNDANT_PACKAGES
                        .fold(content) { acc, p ->
                            acc.replace("$p.", "")
                        }.replace(fixKT51205) {
                            ": ${it.groups[1]!!.value}"
                        }

                    if (newContent != content) {
                        definitionFile.writeText(newContent)
                    }
                }
            }
        }
    }
}
