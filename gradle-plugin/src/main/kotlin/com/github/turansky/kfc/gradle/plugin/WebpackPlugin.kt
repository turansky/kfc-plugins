package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin

// TODO: remove
//   https://youtrack.jetbrains.com/issue/KT-46082
// language=JavaScript
private const val KT_46082_PATCH: String = """
  const resolve = config.resolve
  const alias = resolve.alias = resolve.alias || {}
  const fallback = resolve.fallback = resolve.fallback || {}

  fallback.crypto = false

  function addAlias (moduleName) {
      alias[moduleName + '-jsLegacy'] = moduleName + '-js-legacy'
  }

  addAlias('kotlinx-serialization-kotlinx-serialization-core')
  addAlias('kotlinx-serialization-kotlinx-serialization-json')
"""


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

            patch(KT_46082_PATCH)

            doFirst {
                val momentjsInstalled = project.rootProject.buildDir
                    .resolve("js/node_modules/moment")
                    .exists()

                if (momentjsInstalled && !patches.containsKey(Momentjs.PATCH_NAME)) {
                    patches[Momentjs.PATCH_NAME] = Momentjs.IGNORE_LOCALES_PATCH
                }
            }
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
