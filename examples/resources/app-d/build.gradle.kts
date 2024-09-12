plugins {
    alias(libs.plugins.kfc.application)
}

dependencies {
    jsMainImplementation(kotlinWrappers.js)

    jsMainImplementation(projects.examples.resources.libA)
    jsMainImplementation(projects.examples.resources.libB)
    jsMainImplementation(projects.examples.resources.libC)

    jsTestImplementation(libs.kotlin.testJs)
    jsTestImplementation(libs.coroutines.core)
    jsTestImplementation(libs.coroutines.test)
}
