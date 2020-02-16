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
}
