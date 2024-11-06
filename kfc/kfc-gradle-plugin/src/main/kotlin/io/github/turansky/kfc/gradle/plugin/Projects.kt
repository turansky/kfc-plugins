package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

private val MODULE_NAME = StringProperty("kfc.module.name")

private val BUNDLER = StringProperty("kfc.bundler")

internal val Project.jsModuleName: String
    get() {
        propertyOrNull(MODULE_NAME)
            ?.let { return it }

        return when (this) {
            rootProject -> rootProject.name
            else -> "${rootProject.name}-$name"
        }
    }

internal fun Project.getBundler(): Bundler {
    val value = propertyOrNull(BUNDLER)
        ?: return Vite

    return when (value) {
        "vite" -> Vite

        else -> error("Unexpected bundler: $value")
    }
}

internal fun Project.ext(
    propertyName: String,
    value: Boolean,
) {
    ext(propertyName, value.toString())
}

internal fun Project.ext(
    propertyName: String,
    value: String,
) {
    extensions.extraProperties[propertyName] = value
}
