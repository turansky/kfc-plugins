plugins {
    kotlin("js")
}

kotlin.js {
    browser()
}

dependencies {
    implementation(project(":examples:web-worker:entity"))
}
