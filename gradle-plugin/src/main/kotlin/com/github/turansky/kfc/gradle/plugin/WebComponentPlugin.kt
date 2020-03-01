package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

class WebComponentPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(WebpackPlugin::class)

        val extension = extensions.create<WebComponentExtension>("webcomponent")

        val generateWebComponent = tasks.register<GenerateWebComponent>("generateWebComponent")

        plugins.withType<KotlinJsPluginWrapper> {
            tasks {
                useModularJsTarget()

                configureEach<KotlinWebpack> {
                    outputFileName = COMPONENT_JS
                    sourceMaps = false

                    dependsOn(generateWebComponent)
                }
            }
        }

        afterEvaluate {
            generateWebComponent.configure {
                component = extension.build()
            }

            tasks {
                configureEach<KotlinJsDce> {
                    keep += keepId(extension.source)
                }

                configureEach<PatchWebpackConfig> {
                    patch("output", defaultOutputConfiguration())
                    patch("entry", entryConfiguration(generateWebComponent.get().entry))
                }
            }
        }
    }
}
