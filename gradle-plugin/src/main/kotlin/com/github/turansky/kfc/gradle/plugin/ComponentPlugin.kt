package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.Output.COMPONENT
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

open class ComponentExtension {
    var root: String? = null
}

class ComponentPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinJsPlugin()
        plugins.apply(WebpackPlugin::class)

        val extension = extensions.create<ComponentExtension>("component")

        tasks {
            useModularJsTarget()

            configureEach<KotlinWebpack> {
                outputFileName = COMPONENT.fileName
                sourceMaps = false
            }
        }

        afterEvaluate {
            val componentRoot = extension.root
                ?: return@afterEvaluate

            tasks {
                configureEach<KotlinJsDce> {
                    keepPath(componentRoot)
                }

                configureEach<PatchWebpackConfig> {
                    patch("output", outputConfiguration(componentRoot))
                }
            }
        }
    }
}
