package com.github.turansky.kfc.gradle.plugin

private val SNAPSHOT_SUFFIX = "-SNAPSHOT"
private val DELIMITER = "."

internal fun parseVersion(source: String): Version {
    val version = source.removeSuffix(SNAPSHOT_SUFFIX)

    val parts = version
        .split(DELIMITER)
        .map { it.toInt() }

    check(parts.size == 3)

    return StandardVersion(
        major = parts[0],
        minor = parts[1],
        patch = parts[2],
        snapshot = version != source
    )
}

internal sealed class Version {
    abstract val major: Int
    abstract val minor: Int
    abstract val patch: Int
    abstract val snapshot: Boolean

    override fun toString(): String {
        val version = sequenceOf(major, minor, patch)
            .joinToString(DELIMITER)

        return if (snapshot) {
            "$version$SNAPSHOT_SUFFIX"
        } else {
            version
        }
    }
}

private data class StandardVersion(
    override val major: Int,
    override val minor: Int,
    override val patch: Int,
    override val snapshot: Boolean
) : Version()
