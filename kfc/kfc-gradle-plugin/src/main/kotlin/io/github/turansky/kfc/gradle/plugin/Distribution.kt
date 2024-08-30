package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider

internal fun Project.getProductionDistDirectory(): Provider<Directory> =
    layout.buildDirectory.dir("dist/production")

internal fun Project.getDevelopmentDistDirectory(): Provider<Directory> =
    layout.buildDirectory.dir("dist/development")
