plugins {
    id("io.github.turansky.kfc.application")
}

dependencies {
    jsMainRuntimeOnly(project(":examples:web-worker:view-wl"))
}
