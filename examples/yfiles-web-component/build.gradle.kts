plugins {
    id("io.github.turansky.kfc.component")
}

component {
    export("com.test.example.app.RedButton")
}

dependencies {
    testImplementation(kotlin("test-js"))
}
