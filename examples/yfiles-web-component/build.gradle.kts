plugins {
    id("com.github.turansky.kfc.component")
    id("com.github.turansky.kfc.yfiles")
}

component {
    export("com.test.example.app.RedButton")
}

dependencies {
    testImplementation(kotlin("test-js"))
}
