package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Task
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import java.io.File

internal fun getOutputDirectory(
    task: Task,
): File {
    val project = task.project
    val packageDir = project.tasks.getByName<KotlinJsCompile>("compileKotlinJs")
        .kotlinOptions.outputFile
        .let { project.file(it!!) }
        .parentFile
        .parentFile

    val directoryName = when (task.name) {
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
