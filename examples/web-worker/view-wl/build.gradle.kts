plugins {
    alias(kfc.plugins.application)
}

dependencies {
    jsMainImplementation(kotlinWrappers.browser)
    jsMainImplementation(projects.examples.webWorker.entity)
    jsMainImplementation(projects.examples.webWorker.workerWl)
}
