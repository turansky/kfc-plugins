package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

internal val Project.k2mode: Boolean
    get() {
        val version = findProperty("kotlin.version")
        return version is String && version.startsWith("2.")
    }
