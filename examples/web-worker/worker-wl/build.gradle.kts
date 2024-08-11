plugins {
    alias(libs.plugins.kfc.worker)
}

dependencies {
    jsMainImplementation(libs.wrappers.browser)
    jsMainImplementation(projects.examples.webWorker.entity)
}
