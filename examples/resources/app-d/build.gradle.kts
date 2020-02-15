plugins {
    kotlin("js")
}

kotlin {
    target {
        browser()
    }
}

dependencies {
    implementation(kotlin("stdlib-js"))

    implementation(project(":examples:resources:lib-a"))
    implementation(project(":examples:resources:lib-b"))
    implementation(project(":examples:resources:lib-c"))
}
