plugins {
    kotlin("js")
    id("com.github.turansky.kfc.webpack")
}

kotlin {
    target {
        browser()
    }
}

dependencies {
    implementation(kotlin("stdlib-js"))
}
