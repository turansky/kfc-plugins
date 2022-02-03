plugins {
    id("io.github.turansky.kfc.component")
}

component {
    export("com.test.view.BlueView")
    export("com.test.view.OrangeView")
}

dependencies {
    implementation(project(":examples:webpack-plus-ktor:entity"))
}
