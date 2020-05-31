plugins {
    id("com.github.turansky.kfc.dev-server")
}

devServer {
    root = "com.test.view"

    proxy {
        root = "data"
        source = ":examples:webpack-plus-ktor:ktor-server:run"
        port = 9876
    }
}

dependencies {
    implementation(kotlin("stdlib-js"))

    implementation(project(":examples:webpack-plus-ktor:view"))
}
