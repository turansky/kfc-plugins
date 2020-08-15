plugins {
    id("com.github.turansky.kfc.webcomponent")
}

webcomponent {
    id = "super-puper-view"

    source = "com.test.view.View"
}

dependencies {
    implementation(project(":examples:webpack-plus-ktor:entity"))
}
