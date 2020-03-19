package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Task

internal fun Task.keepId(path: String): String =
    "$jsProjectId.$path"
