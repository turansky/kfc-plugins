package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

internal sealed class Property<T : Any>(val name: String)
internal class StringProperty(name: String) : Property<String>(name)
internal class BooleanProperty(name: String) : Property<Boolean>(name)

internal inline fun <reified T : Any> Project.propertyOrNull(p: Property<T>): T? =
    if (hasProperty(p.name)) {
        property(p.name) as T
    } else {
        null
    }
