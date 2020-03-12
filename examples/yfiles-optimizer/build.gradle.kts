plugins {
    kotlin("js")
    id("com.github.turansky.kfc.yfiles")
}

kotlin.target.browser()

dependencies {
    implementation(kotlin("stdlib-js"))

    testImplementation(kotlin("test-js"))
}
