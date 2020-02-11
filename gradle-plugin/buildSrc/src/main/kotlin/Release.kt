import org.gradle.api.Project

private val GRADLE_PLUGIN_PREFIX = "gradle.plugin."

fun Project.preparePublish() {
    val version = readVersion()
    val releaseVersion = version.copy(snapshot = false)

    changeVersion(releaseVersion)
    changeGroup(false)
}

fun Project.prepareDevelopment() {
    val version = readVersion()
    val snapshotVersion = version.copy(
        patch = version.patch + 1,
        snapshot = true
    )

    changeVersion(snapshotVersion)
    changeGroup(true)
}

private fun Project.changeGroup(addPrefix: Boolean) {
    var group = group.toString()
    group = if (addPrefix) {
        "$GRADLE_PLUGIN_PREFIX$group"
    } else {
        group.removePrefix(GRADLE_PLUGIN_PREFIX)
    }

    changeProperty("group", group)
}

internal fun Project.readVersion(): Version =
    parseVersion(version.toString())

private fun Project.changeVersion(newVersion: Version) {
    changeProperty("version", newVersion.toString())
    version = newVersion.toString()
}
