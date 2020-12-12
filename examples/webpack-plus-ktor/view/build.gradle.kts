plugins {
    id("com.github.turansky.kfc.webcomponent")
}

component {
    export("com.test.view.BlueView")
    export("com.test.view.OrangeView")
}

dependencies {
    implementation(project(":examples:webpack-plus-ktor:entity"))
}
