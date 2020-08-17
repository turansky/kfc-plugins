package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.Output.COMPONENT
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

class WebComponentPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinJsPlugin(distribution = true)
        plugins.apply(WebpackPlugin::class)

        val extension = extensions.create<WebComponentExtension>("webcomponent")

        val generateWebComponent = tasks.registerGenerateWebComponent()

        tasks {
            useModularJsTarget()

            configureEach<KotlinWebpack> {
                outputFileName = COMPONENT.fileName
                sourceMaps = false

                dependsOn(generateWebComponent)
            }
        }

        afterEvaluate {
            generateWebComponent {
                component = extension.build()
            }

            tasks {
                configureEach<KotlinJsDce> {
                    keepPath(extension.source)
                }

                configureEach<PatchWebpackConfig> {
                    patch("output", defaultOutputConfiguration())
                    patch("entry", entryConfiguration(entry = generateWebComponent.get().entry))
                }
            }
        }
    }
}
