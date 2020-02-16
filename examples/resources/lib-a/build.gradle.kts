plugins {
    kotlin("js")
    id("com.github.turansky.kfc.library")
}

library {
    root = "com.test.library"
}

kotlin {
    target {
        browser()
    }
}

dependencies {
    implementation(kotlin("stdlib-js"))
}
