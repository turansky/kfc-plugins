plugins {
    id("com.github.turansky.kfc.custom-element")
    id("com.github.turansky.kfc.webcomponent")
}

webcomponent {
    add("com.test.view.BlueView")
    add("com.test.view.OrangeView")
}

dependencies {
    implementation(project(":examples:webpack-plus-ktor:entity"))
}
