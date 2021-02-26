plugins {
    kotlin("js")
}

kotlin.js {
    moduleName = "ww-view"

    browser()
}

dependencies {
    implementation(project(":examples:web-worker:entity"))
}
