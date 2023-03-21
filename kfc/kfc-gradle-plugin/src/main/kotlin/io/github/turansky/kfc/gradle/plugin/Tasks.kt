package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.Task

internal const val DEFAULT_TASK_GROUP: String = "KFC"

internal const val COMPILE_PRODUCTION: String = "compileProductionExecutableKotlinJs"
internal const val COMPILE_DEVELOPMENT: String = "compileDevelopmentExecutableKotlinJs"
internal const val KOTLIN_SOURCES_TASK = "kotlinSourcesJar"

internal fun Task.eachModuleProjectDependency(
    action: (Project) -> Unit,
) {
    project.relatedModuleProjects()
        .forEach(action)
}
