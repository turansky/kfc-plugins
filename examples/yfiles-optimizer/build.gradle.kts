plugins {
    id("com.github.turansky.kfc.library")
    id("com.github.turansky.kfc.yfiles")
}

dependencies {
    implementation(kotlin("stdlib-js"))

    testImplementation(kotlin("test-js"))
}
