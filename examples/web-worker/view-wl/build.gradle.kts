plugins {
    id("io.github.turansky.kfc.application")
}

dependencies {
    implementation(project(":examples:web-worker:entity"))
    implementation(project(":examples:web-worker:worker-wl"))
}
