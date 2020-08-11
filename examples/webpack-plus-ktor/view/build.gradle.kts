plugins {
    id("com.github.turansky.kfc.webcomponent")
    kotlin("plugin.serialization")
}

webcomponent {
    id = "super-puper-view"

    source = "com.test.view.View"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js")

    implementation(project(":examples:webpack-plus-ktor:entity"))
}
