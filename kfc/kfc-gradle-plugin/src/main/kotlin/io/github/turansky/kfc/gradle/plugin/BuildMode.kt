package io.github.turansky.kfc.gradle.plugin

sealed class BuildMode(
    val bundler: Bundler?,
) {
    object LIBRARY : BuildMode(bundler = null)
    object APPLICATION : BuildMode(bundler = Vite)
}
