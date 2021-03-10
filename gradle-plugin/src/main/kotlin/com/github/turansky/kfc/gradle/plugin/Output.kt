package com.github.turansky.kfc.gradle.plugin

internal enum class Output(val id: String) {
    DEV_SERVER("dev-server");

    val fileName: String
        get() = "${id}.js"
}
