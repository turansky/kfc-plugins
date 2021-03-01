plugins {
    kotlin("js")
}

kotlin.js {
    moduleName = "ww-worker-wl"

    browser()
    useCommonJs()
    binaries.executable()
}

dependencies {
    implementation(project(":examples:web-worker:entity"))

    implementation(ktor("client-js"))
}
