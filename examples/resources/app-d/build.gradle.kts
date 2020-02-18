plugins {
    kotlin("js")
    id("com.github.turansky.kfc.component")
}

kotlin.target.browser()

component {
    root = "Api"
}

dependencies {
    implementation(kotlin("stdlib-js"))

    implementation(project(":examples:resources:lib-a"))
    implementation(project(":examples:resources:lib-b"))
    implementation(project(":examples:resources:lib-c"))

    testImplementation(kotlin("test-js"))
}
