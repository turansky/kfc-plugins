package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.JsModuleKind.COMMON_JS
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Transformer
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
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
            withType<KotlinJsDce>().configureEach {
                enabled = false
            }

            withType<KotlinWebpack>().configureEach {
                if (name !in RUN_TASKS) {
                    enabled = false
                }

                sourceMaps = false
            }
        }

        plugins.withType<KotlinJsPluginWrapper> {
            tasks.withType<KotlinJsCompile>().configureEach {
                kotlinOptions {
                    moduleKind = COMMON_JS
                }
            }

            tasks.named<Jar>(JS_JAR_TASK) {
                from(projectDir) {
                    include(PACKAGE_JSON)
                    filter(packageJsonFilter)
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
