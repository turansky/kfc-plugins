package com.github.turansky.kfc.gradle.plugin

open class ComponentExtension {
    internal val components: MutableList<String> = mutableListOf()

    fun export(component: String) =
        components.add(component)
}
