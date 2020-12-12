plugins {
    id("com.github.turansky.kfc.webcomponent")
}

webcomponent {
    add("com.test.example.app.RedButton")
}

dependencies {
    testImplementation(kotlin("test-js"))
}
