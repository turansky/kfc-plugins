package io.github.turansky.kfc.gradle.plugin

sealed class Bundler(
    val productionPreparationTask: String,
    val developmentPreparationTask: String,
    val productionTask: String,
    val developmentTask: String,
    val configFile: String,
) {
    val runTask: String = "jsViteDev"
}

object Vite : Bundler(
    productionPreparationTask = "jsBrowserProductionPreparationVite",
    developmentPreparationTask = "jsBrowserDevelopmentPreparationVite",
    productionTask = "jsBrowserProductionVite",
    developmentTask = "jsBrowserDevelopmentVite",
    // TODO: We need .mjs extension for now to enable connecting pure ESM plugins
    //  Until Kotlin isn't specifying `type: "module"` in generated `package.json`
    //   Ticket: https://youtrack.jetbrains.com/issue/KT-72680/KJS.-Specify-type-module-in-generated-package.json
    configFile = "vite.config.mjs",
)
