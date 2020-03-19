package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Task
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce

internal fun KotlinJsDce.keepPath(path: String) {
    keep += keepId(path)
}

private fun Task.keepId(path: String): String =
    "$jsProjectId.$path"
