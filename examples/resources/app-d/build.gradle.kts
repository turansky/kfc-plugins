plugins {
    id("io.github.turansky.kfc.application")
}

dependencies {
    jsMainImplementation(project(":examples:resources:lib-a"))
    jsMainImplementation(project(":examples:resources:lib-b"))
    jsMainImplementation(project(":examples:resources:lib-c"))

    jsTestImplementation(kotlin("test-js"))
}
