package io.github.turansky.kfc.gradle.plugin

sealed class Bundler(
    private val name: String,
) {
    val js: BundlerConfiguration =
        BundlerConfiguration(bundler = name, platform = "js")

    val wasmJs: BundlerConfiguration =
        BundlerConfiguration(bundler = name, platform = "wasmJs")
}

class BundlerConfiguration(
    bundler: String,
    val platform: String,
) {
    val production: BundlerConfigurationTasks =
        BundlerConfigurationTasks(
            group = "${platform}BrowserProduction${bundler}",
            compileSyncTask = "${platform}ProductionExecutableCompileSync",
        )

    val development: BundlerConfigurationTasks =
        BundlerConfigurationTasks(
            group = "${platform}BrowserDevelopment${bundler}",
            compileSyncTask = "${platform}DevelopmentExecutableCompileSync",
        )

    val runTask: String = "${platform}${bundler}Dev"
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
