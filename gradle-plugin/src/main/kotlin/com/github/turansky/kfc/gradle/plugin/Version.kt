package com.github.turansky.kfc.gradle.plugin

private val SNAPSHOT_SUFFIX = "-SNAPSHOT"
private val DELIMITER = "."

internal fun parseVersion(
    source: String,
    fixed: Boolean = false
): Version {
    val version = source.removeSuffix(SNAPSHOT_SUFFIX)
    val snapshot = version != source

    val parts = version
        .split(DELIMITER)
        .map { it.toInt() }

    return if (fixed) {
        check(parts.size == if (snapshot) 3 else 4)

        FixedVersion(
            major = parts[0],
            minor = parts[1],
            patch = parts[2],
            date = if (snapshot) null else parts[3]
        )
    } else {
        check(parts.size == 3)

        StandardVersion(
            major = parts[0],
            minor = parts[1],
            patch = parts[2],
            snapshot = snapshot
        )
    }
}

internal sealed class Version {
    protected abstract val major: Int
    protected abstract val minor: Int
    protected abstract val patch: Int
    abstract val snapshot: Boolean

    abstract fun toRelease(): Version
    abstract fun toNextSnapshot(): Version

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
) : Version() {
    override fun toRelease(): Version =
        copy(snapshot = false)

    override fun toNextSnapshot(): Version =
        copy(
            patch = patch + 1,
            snapshot = true
        )
}

private data class FixedVersion(
    override val major: Int,
    override val minor: Int,
    override val patch: Int,
    private val date: Int?
) : Version() {
    override val snapshot: Boolean = date == null

    override fun toRelease(): Version =
        copy(date = currentDate())

    override fun toNextSnapshot(): Version =
        copy(date = null)

    override fun toString(): String =
        super.toString()
            .let { if (snapshot) it else "$it$DELIMITER$date" }
}

private fun currentDate(): Int = TODO()
