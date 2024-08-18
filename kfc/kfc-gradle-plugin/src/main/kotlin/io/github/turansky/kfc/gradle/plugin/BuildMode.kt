package io.github.turansky.kfc.gradle.plugin

enum class BuildMode(
    val distribution: Boolean = true,
    val bundler: Bundler = Bundler.NONE,
) {
    LIBRARY(distribution = false),
    WORKER,
    APPLICATION_WEBPACK(bundler = Bundler.WEBPACK),
    APPLICATION_VITE(bundler = Bundler.VITE),

    ;
}
