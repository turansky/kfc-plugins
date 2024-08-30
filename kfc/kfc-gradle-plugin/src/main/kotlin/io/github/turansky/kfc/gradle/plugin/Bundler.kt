package io.github.turansky.kfc.gradle.plugin

enum class Bundler(
    val developmentTask: String,
    val productionTask: String,
) {
    VITE(
        developmentTask = "jsBrowserDevelopmentVite",
        productionTask = "jsBrowserProductionVite",
    ),
    WEBPACK(
        developmentTask = Webpack.DEVELOPMENT_TASK,
        productionTask = Webpack.PRODUCTION_TASK,
    ),

    ;
}
