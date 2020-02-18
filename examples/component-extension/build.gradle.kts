plugins {
    kotlin("js")
    id("com.github.turansky.kfc.component")
}

kotlin.target.browser()

component {
    root = "com.test.example.app"
}

dependencies {
    implementation(kotlin("stdlib-js"))

    testImplementation(kotlin("test-js"))
}
