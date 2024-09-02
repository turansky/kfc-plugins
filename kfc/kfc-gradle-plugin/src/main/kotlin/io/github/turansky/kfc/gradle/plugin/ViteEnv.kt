@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import nu.studer.java.util.OrderedProperties.OrderedPropertiesBuilder
import org.gradle.api.file.RegularFile
import java.io.PrintWriter
import java.io.StringWriter

fun getViteEnv(
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

    val writer = StringWriter()
    properties.store(PrintWriter(writer), null)
    return writer.buffer.toString()
}
