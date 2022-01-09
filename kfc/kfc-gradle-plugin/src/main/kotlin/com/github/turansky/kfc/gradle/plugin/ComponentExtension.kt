package com.github.turansky.kfc.gradle.plugin

open class ComponentExtension {
    internal val classNames: MutableList<String> = mutableListOf()

    fun export(className: String) =
        classNames.add(className)
}
