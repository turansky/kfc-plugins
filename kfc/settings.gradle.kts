rootProject.name = "kfc"

pluginManagement {
    // TODO: remove after issue fix
    //  https://youtrack.jetbrains.com/issue/KT-57687
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion

        val kfcVersion = extra["kfc.version"] as String
        id("com.github.turansky.kfc.plugin-publish") version kfcVersion

        id("com.gradle.plugin-publish") version "1.1.0"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(":kfc-gradle-plugin")
