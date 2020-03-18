package com.github.turansky.kfc.gradle.plugin

open class WebpackExtension {
    private val outputs = mutableListOf<WebpackOutput>()

    fun output(name: String, root: String) {
        outputs.add(WebpackOutput(name, root))
    }
}

private data class WebpackOutput(
    val name: String,
    val root: String
)
