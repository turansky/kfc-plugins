package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.JvmTarget.JVM_1_8
import com.github.turansky.kfc.gradle.plugin.Output.LIBRARY
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

open class LibraryExtension {
    var root: String? = null
}

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks {
            configureEach<KotlinJsDce> {
                enabled = false
            }

            configureEach<KotlinWebpack> {
                if (name !in DEVELOPMENT_RUN_TASKS) {
                    enabled = false
                }

                outputFileName = LIBRARY.fileName
                sourceMaps = false
            }
        }

        plugins.withType<KotlinMultiplatformPluginWrapper> {
            tasks {
                configureEach<KotlinJvmCompile> {
                    kotlinOptions {
                        jvmTarget = JVM_1_8
                    }
                }

                useModularJsTarget()
            }
        }

        plugins.withType<KotlinJsPluginWrapper> {
            plugins.apply(WebpackPlugin::class)

            val extension = extensions.create<LibraryExtension>("library")

            tasks {
                useModularJsTarget()

                val generateDependencyJson = register<GenerateDependencyJson>("generateDependencyJson")

                named<Jar>(JS_JAR_TASK) {
                    from(generateDependencyJson)
                }
            }

            afterEvaluate {
                val libraryRoot = extension.root
                    ?: return@afterEvaluate

                tasks.configureEach<PatchWebpackConfig> {
                    patch("output", outputConfiguration(libraryRoot))
                }
            }
        }
    }
}

