package io.github.turansky.kfc.gradle.plugin

sealed class Bundler(
    val productionTask: String,
    val developmentTask: String,
    val configFile: String,
) {
    val runTask: String = "jsViteDev"
}

object Vite : Bundler(
    productionTask = "jsBrowserProductionVite",
    developmentTask = "jsBrowserDevelopmentVite",
    configFile = "vite.config.mjs",
)

object Webpack : Bundler(
    productionTask = "jsBrowserProductionWebpack",
    developmentTask = "jsBrowserDevelopmentWebpack",
    configFile = "webpack.config.js",
)
