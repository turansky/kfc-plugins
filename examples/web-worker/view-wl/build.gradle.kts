plugins {
    id("io.github.turansky.kfc.application")
}

dependencies {
    jsMainImplementation(libs.wrappers.browser)
    jsMainImplementation(project(":examples:web-worker:entity"))
    jsMainModule(project(":examples:web-worker:worker-wl"))
}
