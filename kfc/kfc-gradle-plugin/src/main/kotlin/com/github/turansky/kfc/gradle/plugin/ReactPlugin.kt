package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import java.io.File

class ReactPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.configureEach<KotlinJsCompile> {
            doLast {
                addLazySupport(File(kotlinOptions.outputFile!!))
            }
        }
    }
}

private fun addLazySupport(
    file: File,
) {
    if (file.extension != "js")
        return

    val content = file.readText()

    val (bodySource, librariesSource) = content.split("\n  main();\n  return _;\n}(module.exports, ")

    val body = bodySource.substringAfter("{\n")
    val libraryIds = bodySource
        .substringBefore("{\n")
        .removeSurrounding("(function (_, ", ") ")
        .split(", ")

    val imports = librariesSource
        .substringBefore("));")
        .splitToSequence(", ")
        .map { it.removeSurrounding("require(", ")") }
        .mapIndexed { index, library -> "import ${libraryIds[index]} from $library;" }
        .joinToString("\n")

    file.writeText("$imports\n\n$body\n\nmain();")
}
