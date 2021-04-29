package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.TaskContainerScope
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import java.io.File

internal const val DEFAULT_TASK_GROUP: String = "KFC"

internal val DEVELOPMENT_RUN_TASKS = setOf(
    "browserDevelopmentRun"
)

internal val DEVELOPMENT_DCE_TASKS = setOf(
    "processDceDevKotlinJs"
)

internal const val COMPILE_PRODUCTION: String = "compileProductionExecutableKotlinJs"
internal const val COMPILE_DEVELOPMENT: String = "compileDevelopmentExecutableKotlinJs"
internal const val KOTLIN_SOURCES_TASK = "kotlinSourcesJar"

internal val Task.jsModuleName: String
    get() = project.jsModuleName

internal fun Task.jsPackageDir(relative: String): File =
    project.rootProject
        .buildDir
        .resolve("js")
        .resolve("packages")
        .resolve(jsModuleName)
        .resolve(relative)

internal fun TaskContainerScope.useModularJsTarget() {
    configureEach<KotlinJsCompile> {
        kotlinOptions {
            moduleKind = "commonjs"
        }
    }
}

internal fun Task.eachRuntimeProjectDependency(
    action: (Project) -> Unit
) {
    project.relatedRuntimeProjects()
        .forEach(action)
}
