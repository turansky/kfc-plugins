plugins {
    id("io.github.turansky.kfc.worker")
}

dependencies {
    jsMainImplementation(libs.wrappers.browser)
    jsMainImplementation(project(":examples:web-worker:entity"))
}
