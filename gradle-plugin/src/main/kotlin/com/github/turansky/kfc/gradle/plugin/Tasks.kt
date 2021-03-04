package com.github.turansky.kfc.gradle.plugin

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

internal const val JS_SOURCES_JAR_TASK = "jsSourcesJar"

internal val Task.jsProjectId: String
    get() = project.jsProjectId

internal fun Task.jsPackageDir(relative: String): File =
    project.rootProject
        .buildDir
        .resolve("js")
        .resolve("packages")
        .resolve(jsProjectId)
        .resolve(relative)

internal fun TaskContainerScope.useModularJsTarget() {
    configureEach<KotlinJsCompile> {
        kotlinOptions {
            moduleKind = "commonjs"
        }
    }
}
