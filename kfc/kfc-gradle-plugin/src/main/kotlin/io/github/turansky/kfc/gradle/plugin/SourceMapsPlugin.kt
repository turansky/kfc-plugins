package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

internal val SOURCE_MAPS = BooleanProperty("kfc.source.maps")

internal class SourceMapsPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        if (!property(SOURCE_MAPS)) {
            afterEvaluate {
                tasks.configureEach<Kotlin2JsCompile> {
                    compilerOptions {
                        sourceMap.set(false)
                        sourceMapEmbedSources.set(null)

                        // Temp WA
                        sourceMapEmbedSources.convention(null)
                    }
                }
            }
        }
    }
}
