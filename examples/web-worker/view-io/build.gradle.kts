plugins {
    kotlin("js")
}

kotlin.js {
    moduleName = "ww-view-io"

    browser()
}

dependencies {
    implementation(project(":examples:web-worker:entity"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.4.2")
    implementation(ktor("client-js"))
}
