import nu.studer.java.util.OrderedProperties.OrderedPropertiesBuilder
import java.io.File

internal fun setGradleProperty(
    key: String,
    value: String
) {
    val properties = OrderedPropertiesBuilder()
        .withSuppressDateInComment(true)
        .build()

    val file = File("gradle.properties")
    properties.load(file.inputStream())

    properties.setProperty(key, value)
    properties.store(file.writer(), null)
}
