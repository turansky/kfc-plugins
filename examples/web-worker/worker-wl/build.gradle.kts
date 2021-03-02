plugins {
    kotlin("js")
}

kotlin.js {
    moduleName = "wl-worker"

    browser()
    useCommonJs()
    binaries.executable()
}

dependencies {
    implementation(project(":examples:web-worker:entity"))
}
