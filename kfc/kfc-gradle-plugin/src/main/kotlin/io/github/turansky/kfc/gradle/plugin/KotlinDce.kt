package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import java.io.File

internal fun Project.getOutputDirectory(
    task: Task,
): File {
    val packageDir = tasks.getByName<KotlinJsCompile>("compileKotlinJs")
        .kotlinOptions.outputFile
        .let { file(it!!) }
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
