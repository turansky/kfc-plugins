package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

class ComponentPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinJsPlugin(distribution = true)

        val extension = extensions.create<ComponentExtension>("component")

        val generateWebComponent = tasks.registerGenerateExportProxy()

        tasks.configureEach<KotlinWebpack> {
            dependsOn(generateWebComponent)
        }

        afterEvaluate {
            generateWebComponent {
                classNames = extension.classNames
            }

            tasks {
                configureEach<KotlinJsDce> {
                    keepPaths(extension.classNames)
                }

                configureEach<PatchWebpackConfig> {
                    patch("entry", entryConfiguration(entry = generateWebComponent.get().entry))
                }
            }
        }
    }
}
