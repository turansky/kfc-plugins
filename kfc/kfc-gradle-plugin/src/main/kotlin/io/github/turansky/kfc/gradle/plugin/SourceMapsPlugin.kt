package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

private val SOURCE_MAPS = BooleanProperty("kfc.source.maps")

internal class SourceMapsPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        if (!property(SOURCE_MAPS)) {
            afterEvaluate {
                tasks.configureEach<KotlinJsCompile> {
                    kotlinOptions {
                        sourceMap = false
                        sourceMapEmbedSources = null

                        // Temp WA
                        options.sourceMapEmbedSources.convention(null)
                    }
                }

                tasks.configureEach<KotlinWebpack> {
                    sourceMaps = false
                }
            }
        }
    }
}
