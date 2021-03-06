plugins {
    id("com.github.turansky.kfc.dev-server")
}

tasks.patchWebpackConfig {
    val runDir = project.rootProject.buildDir
        .resolve("js/packages")
        .resolve("ww-local-server")
        .resolve("kotlin-dce-dev")

    val view = runDir.resolve("ww-view.js")
    val worker = runDir.resolve("ww-worker.js")

    val viewIo = runDir.resolve("ww-view-io.js")
    val workerIo = runDir.resolve("ww-worker-io.js")

    val viewWl = runDir.resolve("ww-view-wl.js")

    // language=JavaScript
    patch(
        """
        config.entry['view'] = '${view.absolutePath}'
        config.entry['worker'] = '${worker.absolutePath}'
        
        config.entry['view-io'] = '${viewIo.absolutePath}'
        config.entry['worker-io'] = '${workerIo.absolutePath}'
        
        config.entry['view-wl'] = '${viewWl.absolutePath}'
    """
    )
}

dependencies {
    implementation(project(":examples:web-worker:view"))
    implementation(project(":examples:web-worker:worker"))

    implementation(project(":examples:web-worker:view-io"))
    implementation(project(":examples:web-worker:worker-io"))

    implementation(project(":examples:web-worker:view-wl"))
    implementation(project(":examples:web-worker:worker-wl"))
}
