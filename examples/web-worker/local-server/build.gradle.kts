plugins {
    id("io.github.turansky.kfc.application")
}

dependencies {
    jsMainModule(project(":examples:web-worker:view-wl"))
    jsMainImplementation(libs.wrappers.js)
}
