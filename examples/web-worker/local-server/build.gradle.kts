plugins {
    id("io.github.turansky.kfc.application")
}

dependencies {
    jsMainModule(projects.examples.webWorker.viewWl)
    jsMainImplementation(libs.wrappers.js)
}
