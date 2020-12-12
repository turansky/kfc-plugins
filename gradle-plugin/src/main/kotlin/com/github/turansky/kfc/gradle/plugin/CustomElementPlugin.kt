package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce
import java.io.File

private val PROTOTYPE_REGEX = Regex("(\\w+).prototype = Object.create\\(HTMLElement\\.prototype\\);")

private const val FUNCTION_END = "\n  }\n"

internal class CustomElementPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.withType<KotlinJsDce>().configureEach {
            doLast {
                val outputDirectory = getOutputDirectory(name)
                fileTree(outputDirectory).visit {
                    if (file.isCandidate(project.rootProject.name)) {
                        val content = file.readText()
                        val newContent = fixCustomElements(content)
                        if (newContent != content) {
                            file.writeText(newContent)
                        }
                    }
                }
            }
        }
    }
}

private fun Project.getOutputDirectory(name: String): File {
    val packageDir = tasks.getByName<KotlinJsCompile>("compileKotlinJs")
        .kotlinOptions.outputFile
        .let { file(it!!) }
        .parentFile
        .parentFile

    val directoryName = when (name) {
        "processDceKotlinJs" -> "kotlin-dce"
        "processDceDevKotlinJs" -> "kotlin-dce-dev"
        else -> TODO()
    }

    return packageDir.resolve(directoryName)
}

private fun File.isCandidate(projectName: String): Boolean =
    name.endsWith(".js")
            && !name.endsWith(".meta.js")
            && name.startsWith(projectName)

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
        val endIndex = result.indexOf(FUNCTION_END, startIndex)
            .let { if (it != -1) it + FUNCTION_END.length else -1 }

        require(startIndex != -1 && endIndex != -1) {
            "Unable to find constructor for '$name' component"
        }

        result = result.substring(0, endIndex) +
                "\n  }\n  customElements.define('$name', $name);" +
                result.substring(endIndex)

        result = result.replaceFirst(
            constructor,
            "  class $name extends HTMLElement {\n" +
                    "  constructor() {\n" +
                    "    super();\n"
        )
    }

    return result
}
