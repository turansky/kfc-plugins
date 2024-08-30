package io.github.turansky.kfc.gradle.plugin

enum class ViteMode {
    PRODUCTION,
    DEVELOPMENT,

    ;

    override fun toString(): String =
        name.lowercase()
}
