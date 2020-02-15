package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.JsTarget.COMMONJS
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

open class ComponentExtension {
    var root: String? = null
}

class ComponentPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val extension = extensions.create<ComponentExtension>("component")

        plugins.withType<KotlinJsPluginWrapper> {
            tasks {
                withType<KotlinWebpack> {
                    outputFileName = COMPONENT_JS
                    sourceMaps = false
                }

                withType<KotlinJsDce> {
                    extension.root?.let {
                        keep += keepId(it)
                    }
                }

                withType<KotlinJsCompile> {
                    kotlinOptions {
                        moduleKind = COMMONJS
                    }
                }
            }
        }
    }
}
