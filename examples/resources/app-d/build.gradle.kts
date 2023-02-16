plugins {
    id("io.github.turansky.kfc.application")
}

dependencies {
    implementation(project(":examples:resources:lib-a"))
    implementation(project(":examples:resources:lib-b"))
    implementation(project(":examples:resources:lib-c"))

    testImplementation(kotlin("test-js"))
}
