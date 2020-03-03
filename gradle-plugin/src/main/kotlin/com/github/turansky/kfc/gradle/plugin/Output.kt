package com.github.turansky.kfc.gradle.plugin

internal enum class Output(val id: String) {
    COMPONENT("component"),
    LOCAL_SERVER("local-server");

    val fileName: String
        get() = "${id}.js"
}
