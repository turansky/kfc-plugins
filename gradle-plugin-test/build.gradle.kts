plugins {
    id("io.github.turansky.kfc.library")
}

dependencies {
    testImplementation(kotlin("test-js"))
}

tasks.patchWebpackConfig {
    replace("__BUILD_NAME__", "'Frodo'")
    replace("__BUILD_NUMBER__", "42")
}
