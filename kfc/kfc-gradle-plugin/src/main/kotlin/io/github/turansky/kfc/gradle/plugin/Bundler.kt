package io.github.turansky.kfc.gradle.plugin

sealed class Bundler(
    val productionTask: String,
    val developmentTask: String,
)

object Vite : Bundler(
    productionTask = "jsBrowserProductionVite",
    developmentTask = "jsBrowserDevelopmentVite",
)

object Webpack : Bundler(
    productionTask = "jsBrowserProductionWebpack",
    developmentTask = "jsBrowserDevelopmentWebpack",
)
