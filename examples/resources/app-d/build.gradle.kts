plugins {
    id("io.github.turansky.kfc.application")
    id("io.github.turansky.kfc.wrappers")
}

dependencies {
    jsMainImplementation(wrappers("js"))

    jsMainImplementation(project(":examples:resources:lib-a"))
    jsMainImplementation(project(":examples:resources:lib-b"))
    jsMainImplementation(project(":examples:resources:lib-c"))

    jsTestImplementation(kotlin("test-js"))
    jsTestImplementation(libs.coroutines.core)
    jsTestImplementation(libs.coroutines.test)
}
