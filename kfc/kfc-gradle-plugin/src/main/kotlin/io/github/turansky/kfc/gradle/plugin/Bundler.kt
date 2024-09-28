package io.github.turansky.kfc.gradle.plugin

sealed class Bundler(
    val productionTask: String,
    val developmentTask: String,
    val configFile: String,
) {
    // TODO: fix conflict
    val runTask: String = "jsBrowserRun2"
}

object Vite : Bundler(
    productionTask = "jsBrowserProductionVite",
    developmentTask = "jsBrowserDevelopmentVite",
    configFile = "vite.config.js",
)

object Webpack : Bundler(
    productionTask = "jsBrowserProductionWebpack",
    developmentTask = "jsBrowserDevelopmentWebpack",
    configFile = "webpack.config.js",
)
