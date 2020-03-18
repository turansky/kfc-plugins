plugins {
    kotlin("js")
    id("com.github.turansky.kfc.webcomponent")
    id("com.github.turansky.kfc.yfiles")
}

kotlin.target.browser()

webcomponent {
    id = "red-button"

    setter("content")
    property("color")
    property("chromeColor")
    getter("parentButton")

    method("export", "options")
    method("pragmaticExport", "format", "options")

    event("update-start")
    event("update-end")

    source = "com.test.example.app.RedButton"
}

dependencies {
    implementation(kotlin("stdlib-js"))

    testImplementation(kotlin("test-js"))
}
