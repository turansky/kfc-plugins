plugins {
    kotlin("js")
    id("com.github.turansky.kfc.webpack")
}

val MODULE_NAME = "ww-local-server"

kotlin.js {
    moduleName = MODULE_NAME

    browser()
    useCommonJs()
    binaries.executable()
}

tasks.patchWebpackConfig {
    val runDir = project.rootProject.buildDir
        .resolve("js/packages")
        .resolve(MODULE_NAME)
        .resolve("kotlin-dce-dev")

    val view = runDir.resolve("ww-view.js")
    val worker = runDir.resolve("ww-worker.js")

    val viewIo = runDir.resolve("ww-view-io.js")
    val workerIo = runDir.resolve("ww-worker-io.js")

    // language=JavaScript
    patch(
        """
        if (config.mode !== 'development') {
            return
        }
        
        delete config.entry.main

        const output = config.output
        output.filename = '[name].js'
        output.libraryTarget = 'umd'
        delete output.library 
        
        config.entry['view'] = '${view.absolutePath}'
        config.entry['worker'] = '${worker.absolutePath}'
        
        config.entry['view-io'] = '${viewIo.absolutePath}'
        config.entry['worker-io'] = '${workerIo.absolutePath}'
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
