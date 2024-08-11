plugins {
    id("io.github.turansky.kfc.library")
}

dependencies {
    jsTestImplementation(libs.kotlin.test.js)
}

tasks.patchBundlerConfig {
    env("BUILD_NAME", "Frodo")
    env("BUILD_NUMBER", "42")
}
