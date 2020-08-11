plugins {
    id("com.github.turansky.kfc.webcomponent")
}

webcomponent {
    id = "unreal-component"

    source = "com.test.view.Api"
}

dependencies {
    implementation(project(":examples:resources:lib-a"))
    implementation(project(":examples:resources:lib-b"))
    implementation(project(":examples:resources:lib-c"))

    testImplementation(kotlin("test-js"))
}
