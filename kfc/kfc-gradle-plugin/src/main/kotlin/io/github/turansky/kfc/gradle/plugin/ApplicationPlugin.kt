package io.github.turansky.kfc.gradle.plugin

import io.github.turansky.kfc.gradle.plugin.BuildMode.APPLICATION
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val bundler = getBundler()

        val mode = APPLICATION(
            bundler = bundler,
        )
        applyKotlinMultiplatformPlugin(mode)

        plugins.apply(BundlePlugin::class)
    }
}
