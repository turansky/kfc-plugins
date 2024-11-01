rootProject.name = "kfc-plugins"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            val kotlinVersion = extra["kotlin.version"] as String
            plugin("kotlin-multiplatform", "org.jetbrains.kotlin.multiplatform").version(kotlinVersion)
            plugin("kotlin-jsPlainObjects", "org.jetbrains.kotlin.plugin.js-plain-objects").version(kotlinVersion)

            library("kotlin-testJs", "org.jetbrains.kotlin", "kotlin-test-js").version(kotlinVersion)

            val coroutinesVersion = extra["kotlinx-coroutines.version"] as String
            library("coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").version(coroutinesVersion)
            library("coroutines-test", "org.jetbrains.kotlinx", "kotlinx-coroutines-test").version(coroutinesVersion)

            val ktorVersion = extra["ktor.version"] as String
            plugin("ktor", "io.ktor.plugin").version(ktorVersion)
            library("server-core", "io.ktor", "ktor-server-core-jvm").version(ktorVersion)
            library("server-netty", "io.ktor", "ktor-server-netty-jvm").version(ktorVersion)
            library("server-html-builder", "io.ktor", "ktor-server-html-builder").version(ktorVersion)

            val logbackVersion = extra["logback.version"] as String
            library("logback", "ch.qos.logback", "logback-classic").version(logbackVersion)

            val seskarVersion = extra["seskar.version"] as String
            plugin("seskar", "io.github.turansky.seskar").version(seskarVersion)
        }

        create("kfc") {
            val kfcVersion = "--predefined--"
            plugin("application", "io.github.turansky.kfc.application").version(kfcVersion)
            plugin("library", "io.github.turansky.kfc.library").version(kfcVersion)
            plugin("worker", "io.github.turansky.kfc.worker").version(kfcVersion)
        }

        create("kotlinWrappers") {
            val wrappersVersion = extra["kotlin-wrappers.version"] as String
            from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:$wrappersVersion")
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

include("examples:vite:custom-config")
include("examples:vite:simple-app-test")
include("examples:vite:simple-library")
include("examples:vite:simple-vite-app")
include("examples:vite:vite-dev")

include("examples:vite:vite-dev-with-ktor:ktor-app")
include("examples:vite:vite-dev-with-ktor:vite-dev-app")

include("examples:web-worker:entity")
include("examples:web-worker:view-wl")
include("examples:web-worker:worker-wl")
include("examples:web-worker:local-server")

includeBuild("kfc")
