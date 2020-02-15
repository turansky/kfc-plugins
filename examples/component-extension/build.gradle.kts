plugins {
    kotlin("js")
    id("com.github.turansky.kfc.component")
}

component {
    root = "com.test.example.app"
}

kotlin {
    target {
        browser()
    }
}

dependencies {
    implementation(kotlin("stdlib-js"))
}
