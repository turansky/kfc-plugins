plugins {
    kotlin("js")
}

kotlin.js {
    moduleName = "ww-worker-io"

    browser()
    useCommonJs()
    binaries.executable()
}

dependencies {
    implementation(project(":examples:web-worker:entity"))
}
