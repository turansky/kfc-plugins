package com.github.turansky.kfc.gradle.plugin

open class WebpackExtension {
    internal val outputs = mutableListOf<WebpackOutput>()

    fun output(action: WebpackOutput.() -> Unit) {
        outputs.add(WebpackOutput().apply(action))
    }
}

class WebpackOutput {
    var name: String = "<undefined>"
    var root: String = "<undefined>"
}
