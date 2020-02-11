import org.gradle.api.Project

private val GRADLE_PLUGIN_PREFIX = "gradle.plugin."

fun Project.preparePublish() {
    val version = readVersion()
    val releaseVersion = version.toRelease()

    changeVersion(releaseVersion)
    changeGroup(addPrefix = false)
}

fun Project.prepareDevelopment() {
    val version = readVersion()
    val snapshotVersion = version.toNextSnapshot()

    changeVersion(snapshotVersion)
    changeGroup(addPrefix = true)
}

private fun Project.changeGroup(addPrefix: Boolean) {
    var group = group.toString()
    group = if (addPrefix) {
        "$GRADLE_PLUGIN_PREFIX$group"
    } else {
        group.removePrefix(GRADLE_PLUGIN_PREFIX)
    }

    setGradleProperty(GradleProperty.GROUP, group)
}

internal fun Project.readVersion(): Version =
    parseVersion(version.toString())

private fun Project.changeVersion(newVersion: Version) {
    setGradleProperty(GradleProperty.VERSION, newVersion.toString())
    version = newVersion.toString()
}
