plugins {
    id("io.github.turansky.kfc.library")
}

dependencies {
    jsTestImplementation(kotlin("test-js"))
}

tasks.patchWebpackConfig {
    env("BUILD_NAME", "Frodo")
    env("BUILD_NUMBER", "42")
}
