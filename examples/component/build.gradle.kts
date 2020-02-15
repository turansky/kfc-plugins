plugins {
    kotlin("js")
    id("com.github.turansky.kfc.component")
}

kotlin {
    target {
        browser()
    }
}

dependencies {
    implementation(kotlin("stdlib-js"))
}
