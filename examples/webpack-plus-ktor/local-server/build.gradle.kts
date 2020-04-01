plugins {
    kotlin("js")
    id("com.github.turansky.kfc.dev-server")
}

kotlin.target.browser()

localServer {
    root = "com.test.view"

    proxy {
        source = project(":examples:webpack-plus-ktor:server")
        port = 8081
    }
}

dependencies {
    implementation(kotlin("stdlib-js"))

    implementation(project(":examples:webpack-plus-ktor:view"))
}
