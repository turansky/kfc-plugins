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
                val patchWebpackConfig = register<PatchWebpackConfig>("patchWebpackConfig") {
                    addResourceModules()
                }

                named<Delete>("clean") {
                    delete(patchWebpackConfig)
                }

                configureEach<KotlinJsCompile> {
                    dependsOn(patchWebpackConfig)
                }
            }
        }
    }
}

private fun PatchWebpackConfig.addResourceModules() {
    val resources = project.relatedResources()
    if (resources.isEmpty()) {
        return
    }

    val paths = resources.joinToString(",\n") {
        it.toPathString()
    }

    // language=JavaScript
    val body = """
            |config.resolve.modules.unshift(
            |    $paths
            |)
        """.trimMargin()

    patch("resources", body)
}
