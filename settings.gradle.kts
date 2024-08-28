rootProject.name = "kfc-plugins"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            val kotlinVersion = extra["kotlin.version"] as String
            plugin("kotlin-multiplatform", "org.jetbrains.kotlin.multiplatform").version(kotlinVersion)
            plugin("kotlin-js-plain-objects", "org.jetbrains.kotlin.plugin.js-plain-objects").version(kotlinVersion)

            val kfcVersion = "--predefined--"
            plugin("kfc-application", "io.github.turansky.kfc.application").version(kfcVersion)
            plugin("kfc-library", "io.github.turansky.kfc.library").version(kfcVersion)
            plugin("kfc-worker", "io.github.turansky.kfc.worker").version(kfcVersion)

            library("kotlin-test-js", "org.jetbrains.kotlin", "kotlin-test-js").version(kotlinVersion)

            val wrappersVersion = extra["kotlin-wrappers.version"] as String
            from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:$wrappersVersion")

            val coroutinesVersion = extra["kotlinx-coroutines.version"] as String
            library("coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").version(coroutinesVersion)
            library("coroutines-test", "org.jetbrains.kotlinx", "kotlinx-coroutines-test").version(coroutinesVersion)
        }
    }
}

include("examples:assets:lib")
include("examples:assets:lib-multiplatform")

include("examples:js-plain-object")

include("examples:resources:lib-a")
include("examples:resources:lib-b")
include("examples:resources:lib-c")
include("examples:resources:app-d")

include("examples:test-data:first")
include("examples:test-data:second")

include("examples:value-class")

include("examples:vite:simple-app-test")
include("examples:vite:simple-library")
include("examples:vite:simple-webpack-app")

include("examples:web-worker:entity")
include("examples:web-worker:view-wl")
include("examples:web-worker:worker-wl")
include("examples:web-worker:local-server")

include("gradle-plugin-test")

includeBuild("kfc")
