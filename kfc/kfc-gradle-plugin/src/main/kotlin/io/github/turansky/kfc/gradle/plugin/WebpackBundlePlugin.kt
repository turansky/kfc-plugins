package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.targets.js.npm.DevNpmDependencyExtension

private val WEBPACK_BUNDLE_ANALYZE = BooleanProperty("kfc.webpack.bundle.analyze")

// language=JavaScript
private val ANALYZE = """
const { BundleAnalyzerPlugin } = require('webpack-bundle-analyzer')
config.plugins.push(new BundleAnalyzerPlugin())    
""".trimIndent()

class WebpackBundlePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        if (!property(WEBPACK_BUNDLE_ANALYZE))
            return@with

        plugins.apply(WebpackPlugin::class)

        tasks.configureEach<PatchBundlerConfig> {
            patch("bundle-analyze", ANALYZE)
        }

        afterEvaluate {
            plugins.withId(KotlinPlugin.MULTIPLATFORM) {
                dependencies {
                    applyConfiguration(JS_MAIN_IMPLEMENTATION)
                }
            }
        }
    }

    private fun DependencyHandlerScope.applyConfiguration(configurationName: String) {
        val devNpm = extensions.getByName<DevNpmDependencyExtension>("devNpm")

        configurationName(devNpm("webpack-bundle-analyzer", "4.8.0"))
    }
}
