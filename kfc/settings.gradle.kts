rootProject.name = "kfc"

pluginManagement {
    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion

        id("com.gradle.plugin-publish") version "2.0.0"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(":kfc-gradle-plugin")
