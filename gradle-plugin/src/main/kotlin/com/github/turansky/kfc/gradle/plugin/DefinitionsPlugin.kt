package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

private val REDUNDANT_PACKAGES = listOf(
    "org.w3c.dom.url",
    "org.w3c.dom"
)

internal class DefinitionsPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.withType<Kotlin2JsCompile>().configureEach {
            doLast {
                val definitionFile = outputFile.parentFile
                    .resolve(outputFile.name.substringBeforeLast(".") + ".d.ts")

                if (definitionFile.exists()) {
                    val content = definitionFile.readText()
                    val newContent = content

                    if (newContent != content) {
                        definitionFile.writeText(newContent)
                    }
                }
            }
        }
    }
}
