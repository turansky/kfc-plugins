package io.github.turansky.kfc.gradle.plugin

sealed class Bundler(
    private val bundler: String,
) {
    val js: BundlerConfiguration =
        BundlerConfiguration(bundler = bundler, platform = "js")

    val wasmJs: BundlerConfiguration =
        BundlerConfiguration(bundler = bundler, platform = "wasmJs")
}

class BundlerConfiguration(
    bundler: String,
    val platform: String,
) {
    val production: BundlerConfigurationTasks =
        BundlerConfigurationTasks("${platform}BrowserProduction${bundler}")

    val development: BundlerConfigurationTasks =
        BundlerConfigurationTasks("${platform}BrowserDevelopment${bundler}")

    val runTask: String = "${platform}${bundler}Dev"
}

class BundlerConfigurationTasks(
    group: String,
) {
    val prepareTask = group + "Prepare"
    val buildTask = group
}

object Vite : Bundler("vite") {

    // TODO: We need .mjs extension for now to enable connecting pure ESM plugins
    //  Until Kotlin isn't specifying `type: "module"` in generated `package.json`
    //   Ticket: https://youtrack.jetbrains.com/issue/KT-72680/KJS.-Specify-type-module-in-generated-package.json
    val configFile: String = "vite.config.mjs"
}
