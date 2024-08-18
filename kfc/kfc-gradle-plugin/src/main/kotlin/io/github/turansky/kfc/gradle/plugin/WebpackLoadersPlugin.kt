@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.targets.js.npm.DevNpmDependencyExtension

private const val CSS_LOADER = "css-loader"
private const val FILE_LOADER = "file-loader"

// language=JavaScript
private val RESOLVE_RULES: String = """
// WA for MUI
// Details - https://github.com/mui/material-ui/issues/23290
config.module.rules.push({
  test: /\.m?js${'$'}/i,
  resolve: {
    fullySpecified: false,
  },
})
""".trimIndent()

// language=JavaScript
private val CSS_RULES: String = """
config.module.rules.push({
  test: /\.css${'$'}/,
  loader: '$CSS_LOADER',
  options: {
    esModule: false,
  },
})
""".trimIndent()

private fun Project.fontRules(): String {
    val publicPath = outputPath("/", "fonts")
    val outputPath = outputPath("./", "fonts")

    // language=JavaScript
    return """
    config.module.rules.push({ 
      test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?${'$'}/,
      loader: '$FILE_LOADER',
      options: {
        name: '[name].[contenthash].[ext]',
        publicPath: '$publicPath',
        outputPath: '$outputPath',
        esModule: false,
      },
    })
    """.trimIndent()
}

class WebpackLoadersPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(WebpackPlugin::class)

        tasks.configureEach<PatchBundlerConfig> {
            patch("resolve-rules", RESOLVE_RULES)
            patch("css-rules", CSS_RULES)
            patch("font-rules", fontRules())
        }

        afterEvaluate {
            plugins.withId(KotlinPlugin.MULTIPLATFORM) {
                dependencies {
                    applyConfiguration(JS_MAIN_IMPLEMENTATION)
                }
            }
        }
    }

    private fun DependencyHandlerScope.applyConfiguration(configurationName: String) {
        val devNpm = extensions.getByName<DevNpmDependencyExtension>("devNpm")

        configurationName(devNpm(CSS_LOADER, "7.1.2"))
        configurationName(devNpm(FILE_LOADER, "6.2.0"))
    }
}
