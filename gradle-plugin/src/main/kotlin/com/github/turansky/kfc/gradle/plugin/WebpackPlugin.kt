package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper

class WebpackPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        // WA: for Kotlin & Karma
        projectDir.resolve("webpack.config.d").mkdir()

        plugins.withType<KotlinJsPluginWrapper> {
            tasks {
                val patchWebpackConfig = register<WebpackConfigTask>("patchWebpackConfig")

                named<Delete>("clean") {
                    delete(patchWebpackConfig)
                }

                withType<KotlinJsCompile> {
                    dependsOn(patchWebpackConfig)
                }
            }
        }
    }
}
