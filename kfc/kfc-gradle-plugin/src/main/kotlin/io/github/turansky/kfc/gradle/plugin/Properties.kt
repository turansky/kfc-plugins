package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

internal sealed class AbstractProperty<T : Any>(
    val name: String,
)

internal class StringProperty(
    name: String,
) : AbstractProperty<String>(name)

internal class BooleanProperty(
    name: String,
    val default: Boolean = false,
) : AbstractProperty<Boolean>(name)

private fun Project.propertyOrNull(
    propertyName: String,
): String? =
    if (hasProperty(propertyName)) {
        property(propertyName) as String
    } else {
        null
    }

internal fun Project.propertyOrNull(
    property: StringProperty,
): String? =
    propertyOrNull(property.name)

internal fun Project.property(
    property: BooleanProperty,
): Boolean =
    propertyOrNull(property.name)
        ?.toBoolean()
        ?: property.default
