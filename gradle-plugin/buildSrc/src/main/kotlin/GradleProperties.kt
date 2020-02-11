import nu.studer.java.util.OrderedProperties
import java.io.File

internal fun changeProperty(
    key: String,
    value: String
) {
    val properties = OrderedProperties.OrderedPropertiesBuilder()
        .withSuppressDateInComment(true)
        .build()

    val file = File("gradle.properties")
    properties.load(file.inputStream())

    properties.setProperty(key, value)
    properties.store(file.writer(), null)
}
