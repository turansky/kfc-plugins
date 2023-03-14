package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Task
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import java.io.File

internal fun getOutputDirectory(
    task: Task,
): File {
    val project = task.project
    val packageDir = project.tasks
        .getByName<Kotlin2JsCompile>("compileKotlinJs")
        .destinationDirectory
        .asFile.get()
        .parentFile

    val directoryName = when (task.name) {
        "processDceJsKotlinJs",
        -> "kotlin-dce"

        "processDceDevJsKotlinJs",
        -> "kotlin-dce-dev"

        else -> TODO()
    }

    return packageDir.resolve(directoryName)
}
