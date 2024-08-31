@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import nu.studer.java.util.OrderedProperties.OrderedPropertiesBuilder
import java.io.StringWriter

fun getViteEnv(
    variable: List<EnvVariable>,
): String {
    val properties = OrderedPropertiesBuilder()
        .withSuppressDateInComment(true)
        .build()

    for ((key, value) in variable) {
        properties.setProperty("VITE_$key", value)
    }

    val writer = StringWriter()
    properties.store(writer, null)
    return writer.toString()
}
