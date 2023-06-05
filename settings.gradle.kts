rootProject.name = "kfc-plugins"

pluginManagement {
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

include("examples:assets:lib")
include("examples:assets:lib-multiplatform")

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
