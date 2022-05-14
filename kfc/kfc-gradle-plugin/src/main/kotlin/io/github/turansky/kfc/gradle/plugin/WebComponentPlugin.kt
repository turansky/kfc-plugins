package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce
import java.io.File

private val PROTOTYPE_REGEX = Regex("(\\w+).prototype = Object.create\\(HTMLElement\\.prototype\\);")
private val UPPERCASE_REGEX = Regex("([A-Z]+)")

private const val FUNCTION_END = "\n  }\n"
private const val CALLBACK_FUNCTION_END = "\n  };\n"

private val CALLBACKS = listOf(
    "connectedCallback",
    "disconnectedCallback",
    "adoptedCallback",
    "attributeChangedCallback"
)

internal class WebComponentPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.configureEach<KotlinJsDce> {
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

// TODO: move to common
internal fun Project.getOutputDirectory(name: String): File {
    val packageDir = tasks.getByName<KotlinJsCompile>("compileKotlinJs")
        .kotlinOptions.outputFile
        .let { file(it!!) }
        .parentFile
        .parentFile

    val directoryName = when (name) {
        "processDceKotlinJs",
        "processDceJsKotlinJs",
        -> "kotlin-dce"

        "processDceDevKotlinJs",
        "processDceDevJsKotlinJs",
        -> "kotlin-dce-dev"

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

    PROTOTYPE_REGEX.findAll(content).forEach { r ->
        val name = r.groups[1]!!.value

        val prototype = "  $name.prototype = Object.create(HTMLElement.prototype);\n" +
                "  $name.prototype.constructor = $name;\n"

        result = result.replaceFirst(prototype, "")

        val inlinedCallbacks = mutableListOf<String>()
        for (callback in CALLBACKS) {
            val start = "$name.prototype.$callback = function"
            val si = result.indexOf(start)
            if (si == -1)
                continue

            val ei = result.indexOf(CALLBACK_FUNCTION_END, si) +
                    CALLBACK_FUNCTION_END.length + 1

            inlinedCallbacks.add(
                callback + result.substring(
                    si + start.length,
                    ei - 1
                )
            )

            result = result.substring(0, si) + result.substring(ei)
        }

        val constructor = "  function $name() {\n" +
                "    HTMLElement.call(this);\n"

        val startIndex = result.indexOf(constructor)
        val endIndex = result.indexOf(FUNCTION_END, startIndex)
            .let { if (it != -1) it + FUNCTION_END.length else it }

        require(startIndex != -1 && endIndex != -1) {
            "Unable to find constructor for '$name' component"
        }

        result = result.substring(0, endIndex) +
                inlinedCallbacks.joinToString("\n\n") +
                "\n  }\n  customElements.define('${name.toKebabCase()}', $name);\n\n" +
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

private fun String.toKebabCase(): String =
    replace(UPPERCASE_REGEX, "-$1")
        .removePrefix("-")
        .toLowerCase()
