package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.TaskContainerScope
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile

internal const val DEFAULT_TASK_GROUP: String = "KFC"

internal const val COMPILE_PRODUCTION: String = "compileProductionExecutableKotlinJs"
internal const val COMPILE_DEVELOPMENT: String = "compileDevelopmentExecutableKotlinJs"
internal const val KOTLIN_SOURCES_TASK = "kotlinSourcesJar"

internal fun TaskContainerScope.useModularJsTarget() {
    configureEach<KotlinJsCompile> {
        kotlinOptions {
            moduleKind = "commonjs"
        }
    }
}

internal fun Task.eachRuntimeProjectDependency(
    action: (Project) -> Unit,
) {
    project.relatedRuntimeProjects()
        .forEach(action)
}
