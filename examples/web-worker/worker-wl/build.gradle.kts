plugins {
    alias(kfc.plugins.worker)
}

dependencies {
    jsMainImplementation(kotlinWrappers.browser)
    jsMainImplementation(projects.examples.webWorker.entity)
}
