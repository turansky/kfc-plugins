plugins {
    alias(kfc.plugins.application)
}

dependencies {
    jsMainImplementation(kotlinWrappers.browser)
    jsMainImplementation(projects.examples.viteBuild.simpleLibrary)
}
