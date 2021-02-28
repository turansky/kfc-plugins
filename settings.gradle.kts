rootProject.name = "kfc-plugins"

pluginManagement {
    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        kotlin("multiplatform") version kotlinVersion
        kotlin("jvm") version kotlinVersion
        kotlin("js") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
    }
}

include("examples:multiple-output")

include("examples:web-component-extension")
include("examples:yfiles-web-component")

include("examples:serialization")

include("examples:resources:lib-a")
include("examples:resources:lib-b")
include("examples:resources:lib-c")
include("examples:resources:app-d")

include("examples:web-worker:entity")
include("examples:web-worker:view")
include("examples:web-worker:view-io")
include("examples:web-worker:worker")
include("examples:web-worker:worker-io")
include("examples:web-worker:local-server")

include("examples:webpack-plus-ktor:entity")
include("examples:webpack-plus-ktor:ktor-server")
include("examples:webpack-plus-ktor:view")
include("examples:webpack-plus-ktor:webpack-server")

include("examples:yfiles-optimizer")

includeBuild("gradle-plugin")
