plugins {
    kotlin("js")
    id("com.github.turansky.kfc.component")
}

kotlin.target.browser()

component {
    root = "com.test.view"
}

dependencies {
    implementation(kotlin("stdlib-js"))
}
