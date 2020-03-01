package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

open class ComponentExtension {
    var root: String? = null
}

class ComponentPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(WebpackPlugin::class)

        val extension = extensions.create<ComponentExtension>("component")

        plugins.withType<KotlinJsPluginWrapper> {
            tasks {
                useModularJsTarget()

                configureEach<KotlinWebpack> {
                    outputFileName = COMPONENT_JS
                    sourceMaps = false
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

                configureEach<PatchWebpackConfig> {
                    patch("output", outputConfiguration(componentRoot))
                }
            }
        }
    }
}
