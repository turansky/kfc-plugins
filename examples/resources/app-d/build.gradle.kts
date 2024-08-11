plugins {
    id("io.github.turansky.kfc.application")
}

dependencies {
    jsMainImplementation(libs.wrappers.js)

    jsMainImplementation(project(":examples:resources:lib-a"))
    jsMainImplementation(project(":examples:resources:lib-b"))
    jsMainImplementation(project(":examples:resources:lib-c"))

    jsTestImplementation(libs.kotlin.test.js)
    jsTestImplementation(libs.coroutines.core)
    jsTestImplementation(libs.coroutines.test)
}
