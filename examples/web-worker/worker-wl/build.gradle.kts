plugins {
    alias(libs.plugins.kfc.worker)
}

dependencies {
    jsMainImplementation(kotlinWrappers.browser)
    jsMainImplementation(projects.examples.webWorker.entity)
}
