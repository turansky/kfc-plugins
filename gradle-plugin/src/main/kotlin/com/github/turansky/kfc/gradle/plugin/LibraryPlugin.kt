package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.JsTarget.COMMONJS
import com.github.turansky.kfc.gradle.plugin.JvmTarget.JVM_1_8
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Transformer
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

private val RUN_TASKS = setOf(
    "browserDevelopmentRun"
)

private val JS_JAR_TASK = "JsJar"

private val PACKAGE_JSON = "package.json"

open class LibraryExtension {
    var root: String? = null
}

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks {
            withType<KotlinJsDce> {
                enabled = false
            }

            withType<KotlinWebpack> {
                if (name !in RUN_TASKS) {
                    enabled = false
                }

                outputFileName = LIBRARY_JS
                sourceMaps = false
            }
        }

        plugins.withType<KotlinMultiplatformPluginWrapper> {
            tasks {
                withType<KotlinJvmCompile> {
                    kotlinOptions {
                        jvmTarget = JVM_1_8
                    }
                }

                withType<KotlinJsCompile> {
                    kotlinOptions {
                        moduleKind = COMMONJS
                    }
                }
            }
        }

        plugins.withType<KotlinJsPluginWrapper> {
            plugins.apply(WebpackPlugin::class)

            val extension = extensions.create<LibraryExtension>("library")

            tasks {
                withType<KotlinJsCompile> {
                    kotlinOptions {
                        moduleKind = COMMONJS
                    }
                }

                named<Jar>(JS_JAR_TASK) {
                    from(projectDir) {
                        include(PACKAGE_JSON)
                        filter(packageJsonFilter)
                    }
                }
            }

            afterEvaluate {
                val libraryRoot = extension.root
                    ?: return@afterEvaluate

                tasks.withType<WebpackConfigTask> {
                    patch("output", outputConfiguration(libraryRoot))
                }
            }

        }
    }
}

private val Project.packageJsonFilter: Transformer<String, String>
    get() = Transformer {
        it.replace("\${project.name}", name)
            .replace("\${project.version}", version.toString())
    }
