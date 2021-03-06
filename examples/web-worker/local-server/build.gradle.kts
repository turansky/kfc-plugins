plugins {
    id("com.github.turansky.kfc.dev-server")
}

tasks.patchWebpackConfig {
    entry("view", "ww-view")
    entry("worker", "ww-worker")

    entry("view-io", "ww-view-io")
    entry("worker-io", "ww-worker-io")
}

dependencies {
    implementation(project(":examples:web-worker:view"))
    implementation(project(":examples:web-worker:worker"))

    implementation(project(":examples:web-worker:view-io"))
    implementation(project(":examples:web-worker:worker-io"))

    runtimeOnly(project(":examples:web-worker:view-wl"))
}
