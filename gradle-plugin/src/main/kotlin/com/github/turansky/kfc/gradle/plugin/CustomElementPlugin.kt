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

private val PROTOTYPE_REGEX = Regex("(\\w+).prototype = Object.create\\(HTMLElement\\.prototype\\);")

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
    var result = content

    PROTOTYPE_REGEX.findAll(content).forEach {
        val name = it.groups[1]!!.value

        val prototype = "  $name.prototype = Object.create(HTMLElement.prototype);\n" +
                "  $name.prototype.constructor = $name;\n"

        result = result.replaceFirst(prototype, "")

        val constructor = "  function $name() {\n" +
                "    HTMLElement.call(this);\n"

        val startIndex = result.indexOf(constructor)
        val endIndex = result.indexOf("\n  }\n", startIndex)
        require(startIndex != -1 && endIndex != -1) {
            "Unable to find constructor for '$name' component"
        }

        result = result.substring(0, endIndex) + "\n}" + result.substring(endIndex)
        result = result.replaceFirst(
            constructor,
            "  class $name extends HTMLElement {\n" +
                    "  constructor() {\n" +
                    "    super();\n"
        )
    }

    return result
}
