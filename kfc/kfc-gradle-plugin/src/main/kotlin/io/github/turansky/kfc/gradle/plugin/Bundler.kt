package io.github.turansky.kfc.gradle.plugin

enum class Bundler(
    val developmentTask: String,
    val productionTask: String,
) {
    Vite(
        developmentTask = "jsBrowserDevelopmentVite",
        productionTask = "jsBrowserProductionVite",
    ),
    Webpack(
        developmentTask = "jsBrowserDevelopmentWebpack",
        productionTask = "jsBrowserProductionWebpack",
    ),

    ;
}
