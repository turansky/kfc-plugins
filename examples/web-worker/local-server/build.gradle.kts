plugins {
    id("io.github.turansky.kfc.dev-server")
}

dependencies {
    jsMainRuntimeOnly(project(":examples:web-worker:view-wl"))
}
