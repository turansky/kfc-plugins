plugins {
    kotlin("js")
    id("com.github.turansky.kfc.local-server")
}

kotlin.target.browser()

localServer {
    root = "com.test.view"
}

dependencies {
    implementation(kotlin("stdlib-js"))

    implementation(project(":examples:webpack-plus-ktor:view"))
}
