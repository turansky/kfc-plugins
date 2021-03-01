plugins {
    kotlin("js")
}

kotlin.js {
    moduleName = "ww-entity"

    browser()
    useCommonJs()
}

dependencies {
    implementation(devNpm("worker-loader", "3.0.8"))
}
