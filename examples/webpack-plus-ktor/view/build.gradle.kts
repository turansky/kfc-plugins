plugins {
    kotlin("js")
    kotlin("plugin.serialization")

    id("com.github.turansky.kfc.component")
}

kotlin.target.browser()

component {
    root = "com.test.view"
}

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js")
}
