plugins {
    id("com.github.turansky.kfc.library")
}

dependencies {
    implementation(kotlin("stdlib-js"))

    implementation(npm("style-loader", "1.1.3"))
    implementation(npm("css-loader", "3.4.2"))
}
