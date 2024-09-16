package io.github.turansky.kfc.gradle.plugin

enum class ViteMode {
    PRODUCTION,
    DEVELOPMENT,

    ;

    val value: String
        get() = name.lowercase()
}
