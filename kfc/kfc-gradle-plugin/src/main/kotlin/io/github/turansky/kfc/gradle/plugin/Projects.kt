package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

private val MODULE_NAME = StringProperty("kfc.module.name")

private val WASM_JS = BooleanProperty("kfc.wasmjs")

internal val Project.jsModuleName: String
    get() {
        propertyOrNull(MODULE_NAME)
            ?.let { return it }

        return when (this) {
            rootProject -> rootProject.name
            else -> "${rootProject.name}-$name"
        }
    }

internal val Project.wasmJsSupported: Boolean
    get() = property(WASM_JS)

internal val Project.wasmJsModuleName: String
    get() = "$jsModuleName-wasm"

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
