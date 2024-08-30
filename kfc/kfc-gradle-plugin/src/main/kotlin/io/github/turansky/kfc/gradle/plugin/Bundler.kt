package io.github.turansky.kfc.gradle.plugin

enum class Bundler(
    productionTask: String,
    developmentTask: String,
) {
    VITE(
        productionTask = "jsBrowserProductionVite",
        developmentTask = "jsBrowserDevelopmentVite",
    ),
    WEBPACK(
        productionTask = Webpack.PRODUCTION_TASK,
        developmentTask = Webpack.DEVELOPMENT_TASK,
    ),

    ;
}
