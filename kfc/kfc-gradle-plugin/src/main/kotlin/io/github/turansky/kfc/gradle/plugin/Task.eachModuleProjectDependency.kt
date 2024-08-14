package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileTool

internal inline fun <reified T : KotlinCompileTool> Task.linkToOutputOf(
    task: String,
) {
    eachModuleProjectDependency {
        val compile = it.tasks.named<T>(task)

        dependsOn(compile)

        inputs.dir(compile.get().destinationDirectory)
    }
}

private fun Task.eachModuleProjectDependency(
    action: (Project) -> Unit,
) {
    project.relatedModuleProjects()
        .forEach(action)
}
