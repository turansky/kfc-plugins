package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.JsTarget.COMMONJS
import org.gradle.kotlin.dsl.TaskContainerScope
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile

internal val DEVELOPMENT_RUN_TASKS = setOf(
    "browserDevelopmentRun"
)

internal const val JS_JAR_TASK = "JsJar"
internal const val JS_SOURCES_JAR_TASK = "JsSourcesJar"

internal fun TaskContainerScope.useModularJsTarget() {
    configureEach<KotlinJsCompile> {
        kotlinOptions {
            moduleKind = COMMONJS
        }
    }
}
