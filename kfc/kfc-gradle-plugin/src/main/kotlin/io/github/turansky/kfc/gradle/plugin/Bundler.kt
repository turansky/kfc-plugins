package io.github.turansky.kfc.gradle.plugin

sealed class Bundler(
    val developmentTask: String,
    val productionTask: String,
)

object Vite : Bundler(
    developmentTask = "jsBrowserDevelopmentVite",
    productionTask = "jsBrowserProductionVite",
)

object Webpack : Bundler(
    developmentTask = "jsBrowserDevelopmentWebpack",
    productionTask = "jsBrowserProductionWebpack",
)
