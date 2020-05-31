plugins {
    id("com.github.turansky.kfc.component")
}

component {
    root = "com.test.example.app"
}

dependencies {
    implementation(kotlin("stdlib-js"))

    testImplementation(kotlin("test-js"))
}
