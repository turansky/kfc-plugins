import nu.studer.java.util.OrderedProperties.OrderedPropertiesBuilder
import java.io.File

private val GRADLE_PROPERTIES = "gradle.properties"

internal fun setGradleProperty(
    key: String,
    value: String
) {
    val properties = OrderedPropertiesBuilder()
        .withSuppressDateInComment(true)
        .build()

    val file = File(GRADLE_PROPERTIES)
    properties.load(file.inputStream())

    properties.setProperty(key, value)
    properties.store(file.writer(), null)
}
