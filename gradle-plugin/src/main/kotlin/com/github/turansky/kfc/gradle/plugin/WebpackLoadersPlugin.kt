package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.targets.js.npm.DevNpmDependencyExtension

private val FONT_PUBLIC_PATH = StringProperty("kfc.font.public.path")
private val FONT_OUTPUT_PATH = StringProperty("kfc.font.output.path")

private const val CSS_LOADER = "css-loader"
private const val SVG_INLINE_LOADER = "svg-inline-loader"
private const val FILE_LOADER = "file-loader"
private const val WORKER_LOADER = "worker-loader"

private const val IMPLEMENTATION = "implementation"
private const val JS_MAIN_IMPLEMENTATION = "jsMainImplementation"

// language=JavaScript
private val RULES = """
    config.module.rules.push(
      {
        test: /\.css${'$'}/,
        loader: '$CSS_LOADER',
        options: {
          esModule: false
        }
      },
      {
        test: /\.svg${'$'}/,
        loader: '$SVG_INLINE_LOADER'
      }
    )
""".trimIndent()

// language=JavaScript
private fun fontRules(
    publicPath: String?,
    outputPath: String?
): String {
    publicPath ?: return ""
    outputPath ?: return ""

    return """
    config.module.rules.push( 
      { 
        test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?${'$'}/,
        loader: '$FILE_LOADER',
        options: {
          name: '[name].[hash].[ext]',
          publicPath: '$publicPath',
          outputPath: '$outputPath',
          esModule: false
        }
      }
    )
    """.trimIndent()
}

// language=JavaScript
private val TERSER_CONFIGURATION = """
    const TerserPlugin = require('terser-webpack-plugin')
    const terserOptions = {
      output: {
        comments: false
      }
    }

    config.optimization = {
      minimizer: [
        new TerserPlugin({ 
          terserOptions
        })
      ]
    }
""".trimIndent()

class WebpackLoadersPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(WebpackPlugin::class)

        tasks.configureEach<PatchWebpackConfig> {
            patch("rules", RULES)

            val fontRules = fontRules(
                publicPath = propertyOrNull(FONT_PUBLIC_PATH),
                outputPath = propertyOrNull(FONT_OUTPUT_PATH)
            )
            patch("font-rules", fontRules)

            patch("terser-configuration", TERSER_CONFIGURATION)
        }

        afterEvaluate {
            plugins.withId(KotlinPlugin.MULTIPLATFORM) {
                dependencies {
                    applyConfiguration(JS_MAIN_IMPLEMENTATION)
                }
            }

            plugins.withId(KotlinPlugin.JS) {
                dependencies {
                    applyConfiguration(IMPLEMENTATION)
                }
            }
        }
    }

    private fun DependencyHandlerScope.applyConfiguration(configurationName: String) {
        val devNpm = extensions.getByName<DevNpmDependencyExtension>("devNpm")

        configurationName(devNpm(CSS_LOADER, "5.1.1"))
        configurationName(devNpm(SVG_INLINE_LOADER, "0.8.2"))
        configurationName(devNpm(FILE_LOADER, "6.2.0"))
        configurationName(devNpm(WORKER_LOADER, "3.0.8"))
    }
}
