package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider

internal fun Project.getProductionDistDirectory(
    platform: JsPlatform,
): Provider<Directory> =
    layout.buildDirectory.dir("dist/${platform.id}/production")

internal fun Project.getDevelopmentDistDirectory(
    platform: JsPlatform,
): Provider<Directory> =
    layout.buildDirectory.dir("dist/${platform.id}/development")
