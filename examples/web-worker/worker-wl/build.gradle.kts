plugins {
    alias(kfc.plugins.library)
    alias(libs.plugins.seskar)
}

dependencies {
    jsMainImplementation(kotlinWrappers.browser)
    jsMainImplementation(projects.examples.webWorker.entity)
    jsMainImplementation(libs.coroutines.core)
}
