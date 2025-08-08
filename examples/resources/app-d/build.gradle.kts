plugins {
    alias(kfc.plugins.application)
}

dependencies {
    commonMainImplementation(kotlinWrappers.js)

    commonMainImplementation(projects.examples.resources.libA)
    commonMainImplementation(projects.examples.resources.libB)
    commonMainImplementation(projects.examples.resources.libC)

    commonTestImplementation(libs.kotlin.test)
    commonTestImplementation(libs.coroutines.core)
    commonTestImplementation(libs.coroutines.test)
}
