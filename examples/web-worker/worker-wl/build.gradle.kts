plugins {
    id("io.github.turansky.kfc.worker")
}

dependencies {
    jsMainImplementation(libs.wrappers.browser)
    jsMainImplementation(projects.examples.webWorker.entity)
}
