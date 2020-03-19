package com.github.turansky.kfc.gradle.plugin

open class WebpackExtension {
    internal val outputs = mutableListOf<WebpackOutput>()

    fun output(name: String, root: String) {
        outputs.add(WebpackOutput(name, root))
    }
}

internal data class WebpackOutput(
    val name: String,
    val root: String
)
