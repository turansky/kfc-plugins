package io.github.turansky.kfc.gradle.plugin

sealed class Bundler(
    name: String,
) {
    val js: BundlerConfiguration =
        BundlerConfiguration(bundler = name, platform = JsPlatform.js)

    val wasmJs: BundlerConfiguration =
        BundlerConfiguration(bundler = name, platform = JsPlatform.wasmJs)
}

class BundlerConfiguration(
    bundler: String,
    val platform: JsPlatform,
) {
    val production: BundlerConfigurationTasks =
        BundlerConfigurationTasks(
            group = "${platform.name}BrowserProduction${bundler}",
            compileSyncTask = "${platform.name}ProductionExecutableCompileSync",
        )

    val development: BundlerConfigurationTasks =
        BundlerConfigurationTasks(
            group = "${platform.name}BrowserDevelopment${bundler}",
            compileSyncTask = "${platform.name}DevelopmentExecutableCompileSync",
        )

    val runTask: String = "${platform.name}${bundler}Dev"
}

class BundlerConfigurationTasks(
    group: String,
    val compileSyncTask: String,
) {
    val prepareTask = group + "Prepare"
    val buildTask = group
}

object Vite : Bundler("Vite") {

    // TODO: We need .mjs extension for now to enable connecting pure ESM plugins
    //  Until Kotlin isn't specifying `type: "module"` in generated `package.json`
    //   Ticket: https://youtrack.jetbrains.com/issue/KT-72680/KJS.-Specify-type-module-in-generated-package.json
    val configFile: String = "vite.config.mjs"
}
