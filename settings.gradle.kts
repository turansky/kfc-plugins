rootProject.name = "kfc-plugins"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

include("examples:component-extension")
include("examples:component-extension-multiplatform")

include("examples:web-component-extension")

include("examples:resources:lib-a")
include("examples:resources:lib-b")
include("examples:resources:lib-c")
include("examples:resources:app-d")

includeBuild("gradle-plugin")
