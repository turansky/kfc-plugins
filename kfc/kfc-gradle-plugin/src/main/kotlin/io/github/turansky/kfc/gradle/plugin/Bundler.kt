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
