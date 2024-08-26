import java.util.*

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

val props = Properties().apply {
    file("../gradle.properties").inputStream().use { load(it) }
}

fun version(target: String): String {
    val value = props.getProperty("${target}.version")
    requireNotNull(value) { "$target in `.gradle.properties` is not set" }
    return value
}

dependencies {
    implementation(kotlin("gradle-plugin", version("kotlin")))
}
