plugins {
    kotlin("js")
    kotlin("plugin.serialization")
}

kotlin.js {
    browser()
}

dependencies {
    testImplementation(kotlin("test-js"))
}
