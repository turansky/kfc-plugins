package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.JsTarget.COMMONJS
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper

class ComponentPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
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

        plugins.withType<KotlinJsPluginWrapper> {
            tasks.withType<KotlinJsCompile> {
                kotlinOptions {
                    moduleKind = COMMONJS
                }
            }
        }
    }
}
