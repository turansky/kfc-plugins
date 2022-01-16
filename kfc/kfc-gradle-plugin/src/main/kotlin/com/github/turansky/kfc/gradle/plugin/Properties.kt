package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

internal sealed class Property<T : Any>(val name: String)

internal class StringProperty(
    name: String,
) : Property<String>(name)

internal class BooleanProperty(
    name: String,
    val default: Boolean = false,
) : Property<Boolean>(name)

private fun Project.propertyOrNull(propertyName: String): String? =
    if (hasProperty(propertyName)) {
        property(propertyName) as String
    } else {
        null
    }

internal fun Project.propertyOrNull(p: StringProperty): String? =
    propertyOrNull(p.name)

internal fun Project.property(p: BooleanProperty): Boolean =
    propertyOrNull(p.name)
        ?.toBoolean()
        ?: p.default
