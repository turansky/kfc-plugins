plugins {
    id("com.github.turansky.kfc.dev-server")
}

devServer {
    root = "com.test.view"
}

tasks.patchWebpackConfig {
    devProxy("http://localhost:8080")
}

dependencies {
    implementation(project(":examples:webpack-plus-ktor:view"))
}
