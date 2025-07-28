@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import nu.studer.java.util.OrderedProperties.OrderedPropertiesBuilder
import org.gradle.api.Task
import org.gradle.api.file.RegularFile
import java.io.File

internal fun Task.viteEnv(
    variables: List<EnvVariable>,
    entryFile: RegularFile,
): File {
    val file = temporaryDir.resolve(DOT_ENV_LOCAL)
    file.writeText(getViteEnv(variables, entryFile))
    return file
}

private fun getViteEnv(
    variables: List<EnvVariable>,
    entryFile: RegularFile,
): String {
    val properties = OrderedPropertiesBuilder()
        .withSuppressDateInComment(true)
        .build()

    for ((key, value) in variables) {
        properties.setProperty("VITE_$key", value)
    }

    properties.setProperty(ENTRY_PATH, entryFile.asFile.absolutePath)

    return properties.entrySet()
        .joinToString("\n") { (k, v) -> "$k=$v" }
}
