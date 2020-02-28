package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.JsTarget.COMMONJS
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
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
                configureEach<KotlinJsCompile> {
                    kotlinOptions {
                        moduleKind = COMMONJS
                    }
                }

                configureEach<KotlinWebpack> {
                    outputFileName = COMPONENT_JS
                    sourceMaps = false

                    dependsOn(generateWebComponent)
                }
            }
        }

        afterEvaluate {
            val component = extension.build()

            tasks {
                configureEach<KotlinJsDce> {
                    keep += keepId(component.sourceRoot)
                }

                configureEach<PatchWebpackConfig> {
                    patch("output", defaultOutputConfiguration())
                }
            }
        }
    }
}
