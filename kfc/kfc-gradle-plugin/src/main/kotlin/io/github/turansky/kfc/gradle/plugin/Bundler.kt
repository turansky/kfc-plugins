package io.github.turansky.kfc.gradle.plugin

sealed class Bundler(
    val displayName: String,
    val toolName: String,
    val bin: String,
) {
    val js: BundlerConfiguration =
        BundlerConfiguration(bundler = toolName, platform = JsPlatform.js)

    val wasmJs: BundlerConfiguration =
        BundlerConfiguration(bundler = toolName, platform = JsPlatform.wasmJs)
}

class BundlerConfiguration(
    bundler: String,
    val platform: JsPlatform,
) {
    private val suffix: String =
        bundler.replaceFirstChar { it.uppercase() }

    val production: BundlerConfigurationTasks =
        BundlerConfigurationTasks(
            group = "${platform.name}BrowserProduction${suffix}",
            compileSyncTask = "${platform.name}ProductionExecutableCompileSync",
        )

    val development: BundlerConfigurationTasks =
        BundlerConfigurationTasks(
            group = "${platform.name}BrowserDevelopment${suffix}",
            compileSyncTask = "${platform.name}DevelopmentExecutableCompileSync",
        )

    val runTask: String = "${platform.name}${suffix}Dev"
}

class BundlerConfigurationTasks(
    group: String,
    val compileSyncTask: String,
) {
    val prepareTask = group + "Prepare"
    val buildTask = group
}
