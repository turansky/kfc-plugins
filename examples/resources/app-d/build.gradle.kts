plugins {
    alias(kfc.plugins.application)
}

dependencies {
    commonMainImplementation(kotlinWrappers.js)

    commonMainImplementation(projects.examples.resources.libA)
    commonMainImplementation(projects.examples.resources.libB)
    commonMainImplementation(projects.examples.resources.libC)

    jsTestImplementation(libs.kotlin.testJs)
    jsTestImplementation(libs.coroutines.core)
    jsTestImplementation(libs.coroutines.test)
}
