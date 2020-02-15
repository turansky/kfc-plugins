package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.JsModuleKind.COMMON_JS
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Transformer
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType
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
                        jvmTarget = "1.8"
                    }
                }

                withType<KotlinJsCompile> {
                    kotlinOptions {
                        moduleKind = COMMON_JS
                    }
                }
            }
        }

        plugins.withType<KotlinJsPluginWrapper> {
            tasks {
                withType<KotlinJsCompile> {
                    kotlinOptions {
                        moduleKind = COMMON_JS
                    }
                }

                named<Jar>(JS_JAR_TASK) {
                    from(projectDir) {
                        include(PACKAGE_JSON)
                        filter(packageJsonFilter)
                    }
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
