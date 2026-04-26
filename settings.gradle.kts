import org.gradle.api.internal.catalog.DefaultVersionCatalogBuilder

rootProject.name = "kfc-plugins"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("kfc") {
            val kfcVersion = "--predefined--"
            plugin("application", "io.github.turansky.kfc.application").version(kfcVersion)
            plugin("library", "io.github.turansky.kfc.library").version(kfcVersion)
        }

        create("kotlinWrappers") {
            val kotlinWrappersCatalog = named("libs")
                .map { it as DefaultVersionCatalogBuilder }
                .map { it.build() }
                .map { it.getDependencyData("catalogs.kotlinWrappers") }
                .map { "${it.group}:${it.name}:${it.version}" }
                .get()

            from(kotlinWrappersCatalog)
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
