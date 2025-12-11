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

            val ktorVersion = extra["ktor.version"] as String
            plugin("ktor", "io.ktor.plugin").version(ktorVersion)
            library("server-core", "io.ktor", "ktor-server-core-jvm").version(ktorVersion)
            library("server-netty", "io.ktor", "ktor-server-netty-jvm").version(ktorVersion)
            library("server-html-builder", "io.ktor", "ktor-server-html-builder").version(ktorVersion)

            val logbackVersion = extra["logback.version"] as String
            library("logback", "ch.qos.logback", "logback-classic").version(logbackVersion)
        }

        register("kfc") {
            val kfcVersion = "--predefined--"
            plugin("application", "io.github.turansky.kfc.application").version(kfcVersion)
            plugin("library", "io.github.turansky.kfc.library").version(kfcVersion)
            plugin("worker", "io.github.turansky.kfc.worker").version(kfcVersion)
        }

        register("kotlinWrappers") {
            val wrappersVersion = extra["kotlin-wrappers.version"] as String
            from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:$wrappersVersion")
        }
    }
}

include("examples:assets:lib")
include("examples:assets:lib-multiplatform")

include("examples:ecma")

include("examples:js-plain-object")

include("examples:resources:lib-a")
include("examples:resources:lib-b")
include("examples:resources:lib-c")
include("examples:resources:app-d")

include("examples:single-file-app")

include("examples:test-data:first")
include("examples:test-data:second")

include("examples:vite-build:custom-config")
include("examples:vite-build:simple-app-test")
include("examples:vite-build:simple-library")
include("examples:vite-build:simple-vite-app")
include("examples:vite-dev")

include("examples:vite-dev-with-ktor:ktor-app")
include("examples:vite-dev-with-ktor:vite-dev-app")

includeBuild("kfc")
