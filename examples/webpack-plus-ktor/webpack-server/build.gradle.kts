plugins {
    kotlin("js")
    id("com.github.turansky.kfc.dev-server")
}

kotlin.target.browser()

devServer {
    root = "com.test.view"

    proxy {
        source = ":examples:webpack-plus-ktor:ktor-server:run"
        port = 8081
    }
}

dependencies {
    implementation(kotlin("stdlib-js"))

    implementation(project(":examples:webpack-plus-ktor:view"))
}
