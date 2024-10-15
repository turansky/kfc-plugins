@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import nu.studer.java.util.OrderedProperties.OrderedPropertiesBuilder
import org.gradle.api.Task
import org.gradle.api.file.RegularFile
import java.io.File

private const val DOT_ENV = ".env"

internal fun Task.viteEnv(
    variables: List<EnvVariable>,
    entryFile: RegularFile,
    useSourceMaps: Boolean,
): File {
    val file = temporaryDir.resolve(DOT_ENV)
    file.writeText(getViteEnv(variables, entryFile, useSourceMaps))
    return file
}

private fun getViteEnv(
    variables: List<EnvVariable>,
    entryFile: RegularFile,
    useSourceMaps: Boolean,
): String {
    val properties = OrderedPropertiesBuilder()
        .withSuppressDateInComment(true)
        .build()

    for ((key, value) in variables) {
        properties.setProperty("VITE_$key", value)
    }

    properties.setProperty(ENTRY_PATH, entryFile.asFile.absolutePath)
    properties.setProperty(USE_SOURCE_MAPS, useSourceMaps.toString())

    return properties.entrySet()
        .joinToString("\n") { (k, v) -> "$k=$v" }
}
