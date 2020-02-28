package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

internal fun Project.keepId(path: String): String =
    "$jsProjectId.$path"
