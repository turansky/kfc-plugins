plugins {
    id("io.github.turansky.kfc.application")
}

dependencies {
    jsMainImplementation(project(":examples:web-worker:entity"))
    jsMainImplementation(project(":examples:web-worker:worker-wl"))
}
