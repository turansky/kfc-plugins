package io.github.turansky.kfc.gradle.plugin

import java.util.*

sealed class Bundler(
    val toolName: String,
    val bin: String,
    val dependencies: List<Dependency>,
) {
    data class Dependency(
        val name: String,
        val version: String,
    )

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

    val platformSuffix: String =
        platform.name.replaceFirstChar { it.uppercase(Locale.US) }

    val production: BundlerConfigurationTasks =
        BundlerConfigurationTasks(
            group = "${platform.name}BrowserProduction${suffix}",
            compileTask = "compileProductionExecutableKotlin$platformSuffix",
            compileSyncTask = "${platform.name}ProductionExecutableCompileSync",
        )

    val development: BundlerConfigurationTasks =
        BundlerConfigurationTasks(
            group = "${platform.name}BrowserDevelopment${suffix}",
            compileTask = "compileDevelopmentExecutableKotlin$platformSuffix",
            compileSyncTask = "${platform.name}DevelopmentExecutableCompileSync",
        )

    val runTask: String = "${platform.name}${suffix}Dev"
}

class BundlerConfigurationTasks(
    group: String,
    val compileTask: String,
    val compileSyncTask: String,
) {
    val prepareTask = group + "Prepare"
    val buildTask = group
}
