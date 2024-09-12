plugins {
    alias(libs.plugins.kfc.application)
}

dependencies {
    jsMainImplementation(kotlinWrappers.browser)
    jsMainImplementation(projects.examples.webWorker.entity)
    jsMainModule(projects.examples.webWorker.workerWl)
}
