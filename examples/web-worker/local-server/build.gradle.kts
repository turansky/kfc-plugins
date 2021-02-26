plugins {
    kotlin("js")
}

kotlin.js {
    moduleName = "ww-local-server"

    browser()
    useCommonJs()
    binaries.executable()
}

dependencies {
    implementation(project(":examples:web-worker:view"))
    implementation(project(":examples:web-worker:worker"))
}
