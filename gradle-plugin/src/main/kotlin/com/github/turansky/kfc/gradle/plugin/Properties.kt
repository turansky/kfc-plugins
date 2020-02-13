package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

internal fun Project.propertyOrNull(s: String): String? =
    if (hasProperty(s)) property(s) as String else null
