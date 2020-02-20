plugins {
    kotlin("js")
    id("com.github.turansky.kfc.library")
}

kotlin {
    target {
        browser()
    }
}

dependencies {
    implementation(kotlin("stdlib-js"))

    implementation(npm("style-loader", "1.1.3"))
    implementation(npm("css-loader", "3.4.2"))
}
