plugins {
    kotlin("js")
    id("com.github.turansky.kfc.webcomponent")
}

kotlin.target.browser()

webcomponent {
    id = "red-button"
    source = "com.test.example.app.Api.RedButton"

    setter("content")
    property("color")
    property("chromeColor")
    getter("parentButton")

    event("update-start")
    event("update-end")
}

dependencies {
    implementation(kotlin("stdlib-js"))

    testImplementation(kotlin("test-js"))
}
