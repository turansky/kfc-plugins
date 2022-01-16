plugins {
    id("com.github.turansky.kfc.library")
}

dependencies {
    testImplementation(kotlin("test-js"))
}

tasks.patchWebpackConfig {
    replace("__BUILD_NUMBER__", "||27||")
}
