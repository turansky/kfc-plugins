package io.github.turansky.kfc.gradle.plugin

import java.util.*

enum class JsPlatform {
    js,
    wasmJs,

    ;

    val id: String =
        name.lowercase(Locale.US)
}
