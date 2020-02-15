package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

internal fun Project.keepId(path: String): String =
    "$jsProjectId.$path"

private val Project.jsProjectId: String
    get() = "${rootProject.name}-$name"
