package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin

// WA for https://youtrack.jetbrains.com/issue/KT-46162
private class WebpackRootPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.withType<NodeJsRootPlugin> {
            the<NodeJsRootExtension>().versions.apply {
                webpack.version = "5.37.0"
                webpackCli.version = "4.7.0"
                webpackDevServer.version = "4.0.0-beta.3"
            }
        }
    }
}

class WebpackPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        rootProject.plugins.apply(WebpackRootPlugin::class)

        plugins.withId(KotlinPlugin.MULTIPLATFORM) {
            tasks {
                applyConfiguration()
            }
        }

        plugins.withId(KotlinPlugin.JS) {
            tasks {
                applyConfiguration()
            }
        }
    }

    private fun TaskContainerScope.applyConfiguration() {
        val patchWebpackConfig = register<PatchWebpackConfig>("patchWebpackConfig") {
            addResourceModules()

            if (project.property(Momentjs.IGNORE_LOCALES_FLAG))
                patch("momentjs-locales-ignore", Momentjs.IGNORE_LOCALES_PATCH)

            // TODO: remove
            //   https://youtrack.jetbrains.com/issue/KT-46082
            // language=JavaScript
            patch(
                """
                if (!config.resolve.fallback)
                    config.resolve.fallback = {}
                    
                config.resolve.fallback.crypto = false
                """
            )
        }

        named<Delete>("clean") {
            delete(patchWebpackConfig)
        }

        configureEach<KotlinJsCompile> {
            dependsOn(patchWebpackConfig)
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
        config.resolve.modules.unshift(
            $paths
        )
    """.trimIndent()

    patch("resources", body)
}
