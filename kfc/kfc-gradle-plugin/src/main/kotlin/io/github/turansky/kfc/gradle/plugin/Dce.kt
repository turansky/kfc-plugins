package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Task
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce

internal fun KotlinJsDce.keepPath(path: String) {
    keep += keepId(path)
}

internal fun KotlinJsDce.keepPaths(paths: Iterable<String>) {
    for (path in paths) {
        keepPath(path)
    }
}

private fun Task.keepId(path: String): String =
    "$jsModuleName.$path"
