plugins {
    id("com.github.turansky.kfc.component")
    kotlin("plugin.serialization")
}

component {
    root = "com.test.view"
}

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js")

    implementation(project(":examples:webpack-plus-ktor:entity"))
}
