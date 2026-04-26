rootProject.name = "kfc-plugins"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        register("libs") {
            val kotlinVersion = extra["kotlin.version"] as String
            plugin("kotlin-multiplatform", "org.jetbrains.kotlin.multiplatform").version(kotlinVersion)
            plugin("kotlin-jsPlainObjects", "org.jetbrains.kotlin.plugin.js-plain-objects").version(kotlinVersion)

            library("kotlin-test", "org.jetbrains.kotlin", "kotlin-test").version(kotlinVersion)

            val coroutinesVersion = extra["kotlinx-coroutines.version"] as String
            library("coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").version(coroutinesVersion)
            library("coroutines-test", "org.jetbrains.kotlinx", "kotlinx-coroutines-test").version(coroutinesVersion)
        }

        register("kfc") {
            val kfcVersion = "--predefined--"
            plugin("application", "io.github.turansky.kfc.application").version(kfcVersion)
            plugin("library", "io.github.turansky.kfc.library").version(kfcVersion)
        }

        register("kotlinWrappers") {
            val wrappersVersion = extra["kotlin-wrappers.version"] as String
            from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:$wrappersVersion")
        }
    }
}

include("examples:ecma")

include("examples:js-plain-object")

include("examples:single-file-app")

include("examples:test-data:first")
include("examples:test-data:second")

include("examples:vite-build:custom-config")
include("examples:vite-build:simple-app-test")
include("examples:vite-build:simple-library")
include("examples:vite-build:simple-vite-app")
include("examples:vite-dev")

includeBuild("kfc")
