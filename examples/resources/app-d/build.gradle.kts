plugins {
    id("io.github.turansky.kfc.application")
}

dependencies {
    jsMainImplementation(libs.wrappers.js)

    jsMainImplementation(projects.examples.resources.libA)
    jsMainImplementation(projects.examples.resources.libB)
    jsMainImplementation(projects.examples.resources.libC)

    jsTestImplementation(libs.kotlin.test.js)
    jsTestImplementation(libs.coroutines.core)
    jsTestImplementation(libs.coroutines.test)
}
