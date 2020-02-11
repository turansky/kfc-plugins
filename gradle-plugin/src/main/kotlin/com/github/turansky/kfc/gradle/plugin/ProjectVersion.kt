package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

internal fun Project.setReleaseVersion() {
    changeVersion(currentVersion.toRelease())
}

internal fun Project.setNextSnapshotVersion() {
    changeVersion(currentVersion.toNextSnapshot())
}

private val Project.currentVersion: Version
    get() = parseVersion(version.toString())

private fun Project.changeVersion(newVersion: Version) {
    setGradleProperty(GradleProperty.VERSION, newVersion.toString())
    version = newVersion.toString()
}
