plugins {
    kotlin("js")
}

kotlin.js {
    moduleName = "ww-view"

    browser()
    useCommonJs()
}

dependencies {
    implementation(project(":examples:web-worker:entity"))
}
