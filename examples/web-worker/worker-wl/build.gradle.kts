plugins {
    id("io.github.turansky.kfc.worker")
    id("io.github.turansky.kfc.wrappers")
}

dependencies {
    jsMainImplementation(wrappers("browser"))
    jsMainImplementation(project(":examples:web-worker:entity"))
}
