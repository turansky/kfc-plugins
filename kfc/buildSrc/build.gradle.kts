import java.util.*

plugins {
    `kotlin-dsl`
}

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

val copySources by tasks.creating(Copy::class) {
    from("../kfc-gradle-plugin/src/main/kotlin/io/github/turansky/kfc/gradle/plugin") {
        include("GradleExtensions.kt")
        include("GradleProperties.kt")
        include("GradleProperty.kt")
        include("MavenPom.kt")
        include("PluginPublishPlugin.kt")
        include("ProjectVersion.kt")
        include("Tasks.kt")
        include("Version.kt")
    }

    into(temporaryDir)
}

kotlin.sourceSets.main {
    kotlin.srcDir(copySources)
}
