plugins {
    alias(kfc.plugins.application)
}

dependencies {
    jsMainModule(projects.examples.webWorker.viewWl)
    jsMainImplementation(kotlinWrappers.js)
}
