plugins {
    alias(libs.plugins.kfc.application)
}

dependencies {
    jsMainModule(projects.examples.webWorker.viewWl)
    jsMainImplementation(kotlinWrappers.js)
}
