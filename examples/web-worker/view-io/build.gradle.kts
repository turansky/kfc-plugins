plugins {
    kotlin("js")
}

kotlin.js {
    moduleName = "ww-view-io"

    browser()
}

dependencies {
    implementation(project(":examples:web-worker:entity"))
}
