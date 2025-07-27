package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

private val MODULE_NAME = StringProperty("kfc.module.name")

private val PLATFORM = StringProperty("kfc.platform")

private val Project.defaultModuleName: String
    get() {
        propertyOrNull(MODULE_NAME)
            ?.let { return it }

        return when (this) {
            rootProject -> rootProject.name
            else -> "${rootProject.name}-$name"
        }
    }

internal fun Project.getModuleName(
    platform: JsPlatform,
): String =
    when (platform) {
        JsPlatform.js -> jsModuleName
        JsPlatform.wasmJs -> wasmModuleName
    }

internal val Project.jsModuleName: String
    get() = when (kfcPlatform) {
        KfcPlatform.JS -> defaultModuleName
        else -> "$defaultModuleName-js"
    }

internal val Project.wasmModuleName: String
    get() = when (kfcPlatform) {
        KfcPlatform.WASM_JS -> defaultModuleName
        else -> "$defaultModuleName-wasm"
    }

internal val Project.kfcPlatform: KfcPlatform
    get() = KfcPlatform.get(propertyOrNull(PLATFORM))

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
