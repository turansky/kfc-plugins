package io.github.turansky.kfc.gradle.plugin

import java.io.File

internal fun File.toPathString(): String =
    absolutePath
        .replace("\\", "\\\\")
        .let { "'$it'" }
