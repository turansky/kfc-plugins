plugins {
    kotlin("js")
}

kotlin.js {
    browser()
}

dependencies {
    implementation(project(":examples:web-worker:view"))
    implementation(project(":examples:web-worker:worker"))
}
