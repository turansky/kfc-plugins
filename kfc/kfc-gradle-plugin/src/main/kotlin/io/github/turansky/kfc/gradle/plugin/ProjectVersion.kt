package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

private val VERSION_FIXED = BooleanProperty("kfc.version.fixed")

internal fun Project.setReleaseVersion() {
    changeVersion(currentVersion.toRelease())
}

internal fun Project.setNextSnapshotVersion() {
    changeVersion(currentVersion.toNextSnapshot())
}

internal val Project.currentVersion: Version
    get() = parseVersion(version.toString(), property(VERSION_FIXED))

private fun Project.changeVersion(newVersion: Version) {
    setGradleProperty(GradleProperty.VERSION, newVersion.toString())
    version = newVersion.toString()
}
