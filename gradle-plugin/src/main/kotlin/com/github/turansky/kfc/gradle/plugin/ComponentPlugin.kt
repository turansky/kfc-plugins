package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.JsTarget.COMMONJS
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper

open class ComponentExtension {
    var root: String? = null
}

class ComponentPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(WebpackPlugin::class)

        val extension = extensions.create<ComponentExtension>("component")

        configure<KotlinJsProjectExtension> {
            target {
                browser {
                    webpackTask {
                        outputFileName = COMPONENT_JS
                        sourceMaps = false
                    }
                }
            }
        }

        afterEvaluate {
            val componentRoot = extension.root
                ?: return@afterEvaluate

            tasks {
                configureEach<KotlinJsDce> {
                    keep += keepId(componentRoot)
                }

                configureEach<WebpackConfigTask> {
                    patch("output", outputConfiguration(componentRoot))
                }
            }
        }

        plugins.withType<KotlinJsPluginWrapper> {
            tasks.configureEach<KotlinJsCompile> {
                kotlinOptions {
                    moduleKind = COMMONJS
                }
            }
        }
    }
}
