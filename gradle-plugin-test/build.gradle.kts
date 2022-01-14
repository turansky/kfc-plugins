plugins {
    id("com.github.turansky.kfc.library")
    id("com.github.turansky.kfc.webpack")
}

dependencies {
    testImplementation(kotlin("test-js"))
}

tasks.patchWebpackConfig {
    replace("__BUILD_NUMBER__", "||27||")
}
