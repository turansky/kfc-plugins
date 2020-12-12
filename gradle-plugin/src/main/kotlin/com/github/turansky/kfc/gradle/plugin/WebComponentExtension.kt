package com.github.turansky.kfc.gradle.plugin

open class WebComponentExtension {
    internal val components: MutableList<String> = mutableListOf()

    fun add(component: String) =
        components.add(component)
}
