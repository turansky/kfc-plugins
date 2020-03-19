plugins {
    kotlin("js")
    id("com.github.turansky.kfc.webpack")
}

kotlin.target.browser()

webpack {
    output(
        name = "content",
        root = "com.test.example.content"
    )

    output(
        name = "background",
        root = "com.test.example.background"
    )
}

dependencies {
    implementation(kotlin("stdlib-js"))

    testImplementation(kotlin("test-js"))
}
