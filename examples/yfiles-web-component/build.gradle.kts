plugins {
    id("com.github.turansky.kfc.webcomponent")
    id("com.github.turansky.kfc.yfiles")
}

component {
    export("com.test.example.app.RedButton")
}

dependencies {
    testImplementation(kotlin("test-js"))
}
