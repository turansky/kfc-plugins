package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.JsModuleKind.COMMON_JS
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

class ComponentPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit =
        with(target) {
            tasks {
                withType<KotlinWebpack>().configureEach {
                    sourceMaps = false
                }
            }

            plugins.withType<KotlinJsPluginWrapper> {
                tasks.withType<KotlinJsCompile>().configureEach {
                    kotlinOptions {
                        moduleKind = COMMON_JS
                    }
                }
            }
        }
}
