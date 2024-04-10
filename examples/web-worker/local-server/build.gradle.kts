plugins {
    id("io.github.turansky.kfc.application")
    id("io.github.turansky.kfc.wrappers")
}

dependencies {
    jsMainModule(project(":examples:web-worker:view-wl"))
    jsMainImplementation(wrappers("js"))
}
