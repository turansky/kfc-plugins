package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider

internal fun Project.getProductionDistDirectory(
    platformId: String,
): Provider<Directory> =
    layout.buildDirectory.dir("dist/$platformId/production")

internal fun Project.getDevelopmentDistDirectory(
    platformId: String,
): Provider<Directory> =
    layout.buildDirectory.dir("dist/$platformId/development")
