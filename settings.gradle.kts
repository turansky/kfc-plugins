rootProject.name = "kfc-plugins"

pluginManagement {
    // TODO: remove after issue fix
    //  https://youtrack.jetbrains.com/issue/KT-57687
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        kotlin("multiplatform") version kotlinVersion
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include("examples:resources:lib-a")
include("examples:resources:lib-b")
include("examples:resources:lib-c")
include("examples:resources:app-d")

include("examples:value-class")

include("examples:web-worker:entity")
include("examples:web-worker:view-wl")
include("examples:web-worker:worker-wl")
include("examples:web-worker:local-server")

include("gradle-plugin-test")

includeBuild("kfc")
