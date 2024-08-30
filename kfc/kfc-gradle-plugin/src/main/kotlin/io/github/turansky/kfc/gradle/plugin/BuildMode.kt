package io.github.turansky.kfc.gradle.plugin

sealed class BuildMode(
    val distribution: Boolean = true,
    val bundler: Bundler? = null,
) {
    object LIBRARY : BuildMode(distribution = false)
    object WORKER : BuildMode()
    object APPLICATION_WEBPACK : BuildMode(bundler = Bundler.WEBPACK)
    object APPLICATION_VITE : BuildMode(bundler = Bundler.VITE)
}
