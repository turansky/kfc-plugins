plugins {
    id("com.github.turansky.kfc.webcomponent")
}

component {
    export("com.test.example.app.RedButton")
}

dependencies {
    testImplementation(kotlin("test-js"))
}
