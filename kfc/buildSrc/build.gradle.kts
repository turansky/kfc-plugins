import java.util.*

repositories {
    gradlePluginPortal()
}

val props = Properties().apply {
    file("../gradle.properties").inputStream().use { load(it) }
}

fun version(target: String): String =
    props.getProperty("${target}.version")

dependencies {
    implementation("nu.studer:java-ordered-properties:1.0.4")
    implementation(kotlin("gradle-plugin", version("kotlin")))
}
