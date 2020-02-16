package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

class WebpackPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.withType<KotlinJsPluginWrapper> {
            tasks {
                val patchWebpackConfig = register<WebpackConfigTask>("patchWebpackConfig") {
                    resources = relatedResources()
                }

                withType<KotlinWebpack> {
                    dependsOn(patchWebpackConfig)
                }
            }
        }
    }
}
