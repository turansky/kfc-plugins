plugins {
    kotlin("js")
    id("com.github.turansky.kfc.webpack")
}

kotlin.target.browser()

webpack {
    output("content", "com.test.example.content")
    output("background", "com.test.example.background")
}

dependencies {
    implementation(kotlin("stdlib-js"))

    testImplementation(kotlin("test-js"))
}
