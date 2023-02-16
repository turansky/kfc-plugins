plugins {
    id("io.github.turansky.kfc.dev-server")
}

dependencies {
    runtimeOnly(project(":examples:web-worker:view-wl"))
}
