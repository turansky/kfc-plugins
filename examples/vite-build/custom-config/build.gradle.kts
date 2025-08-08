plugins {
    alias(kfc.plugins.application)
}

dependencies {
    commonMainImplementation(kotlinWrappers.browser)
    commonMainImplementation(projects.examples.viteBuild.simpleLibrary)
}
