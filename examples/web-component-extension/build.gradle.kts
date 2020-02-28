plugins {
    kotlin("js")
    id("com.github.turansky.kfc.webcomponent")
}

kotlin.target.browser()

webcomponent {
    source = "com.test.example.app.Api.RedButton"
}

dependencies {
    implementation(kotlin("stdlib-js"))

    testImplementation(kotlin("test-js"))
}
