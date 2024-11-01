package io.github.turansky.kfc.gradle.plugin

sealed class BuildMode(
    val distribution: Boolean = true,
    val bundler: Bundler? = null,
) {
    object LIBRARY : BuildMode(distribution = false)
    class APPLICATION(
        bundler: Bundler,
    ) : BuildMode(
        bundler = bundler,
    )
}
