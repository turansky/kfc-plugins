plugins {
    alias(kfc.plugins.application)
    alias(libs.plugins.seskar)
}

dependencies {
    jsMainImplementation(projects.examples.webWorker.viewWl)
    jsMainImplementation(kotlinWrappers.js)
}
