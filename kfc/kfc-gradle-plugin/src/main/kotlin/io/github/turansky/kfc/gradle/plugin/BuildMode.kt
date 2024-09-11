package io.github.turansky.kfc.gradle.plugin

sealed class BuildMode(
    val distribution: Boolean = true,
    val bundler: Bundler? = null,
    val run: Boolean = false,
) {
    object LIBRARY : BuildMode(distribution = false)
    object WORKER : BuildMode()
    class APPLICATION(
        bundler: Bundler,
        run: Boolean,
    ) : BuildMode(
        bundler = bundler,
        run = run,
    )
}
