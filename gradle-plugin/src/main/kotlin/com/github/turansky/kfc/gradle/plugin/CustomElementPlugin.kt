package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile

private object KotlinJs {
    val COMPILE_TASK_NAMES = setOf(
        "compileKotlinJs",
        "compileTestKotlinJs"
    )

    val TARGET_V5 = "v5"
}

internal class CustomElementPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.withType<KotlinJsCompile>().configureEach {
            doLast {
                val jsTarget = kotlinOptions.target
                if (jsTarget != KotlinJs.TARGET_V5) {
                    logger.warn("Unsupported JS target '$jsTarget'!")
                    return@doLast
                }

                if (name !in KotlinJs.COMPILE_TASK_NAMES)
                    return@doLast

                val outputFile = file(kotlinOptions.outputFile!!)
                // IR invalid folder check
                if (outputFile.parentFile.name != "kotlin")
                    return@doLast

                val content = outputFile.readText()
                val newContent = fixCustomElements(content)
                if (newContent != content) {
                    outputFile.writeText(newContent)
                }
            }
        }
    }
}

private fun fixCustomElements(content: String): String {
    // TODO: fix elements

    return content
}
