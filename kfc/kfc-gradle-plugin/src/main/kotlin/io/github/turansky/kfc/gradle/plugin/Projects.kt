package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

private val MODULE_NAME = StringProperty("kfc.module.name")

private val BUNDLER = StringProperty("kfc.bundler")

private val OUTPUT_PATH = StringProperty("kfc.output.path")
private val OUTPUT_NAME = StringProperty("kfc.output.name")
private val OUTPUT_HASH = BooleanProperty("kfc.output.hash")

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

private val Project.jsOutputPath: String?
    get() = propertyOrNull(OUTPUT_PATH)

internal fun Project.outputPath(
    path: String,
): String =
    outputPath("", path)

internal fun Project.outputPath(
    prefix: String,
    suffix: String,
): String {
    val basePath = jsOutputPath
        ?.let { "$it/" }
        ?: ""

    return prefix + basePath + suffix
}

internal val Project.jsOutputName: String
    get() = propertyOrNull(OUTPUT_NAME)
        ?: jsModuleName

internal val Project.jsOutputFileName: String
    get() {
        val hash = if (property(OUTPUT_HASH)) "[contenthash]" else null
        val path = sequenceOf(jsOutputName, hash, "js")
            .filterNotNull()
            .joinToString(".")

        return outputPath(path)
    }

internal val Project.jsChunkFileName: String
    get() = outputPath("[name].[contenthash].js")

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
