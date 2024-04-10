plugins {
    id("io.github.turansky.kfc.application")
    id("io.github.turansky.kfc.wrappers")
}

dependencies {
    jsMainImplementation(wrappers("browser"))
    jsMainImplementation(project(":examples:web-worker:entity"))
    jsMainModule(project(":examples:web-worker:worker-wl"))
}
