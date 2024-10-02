@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.targets.js.npm.DevNpmDependencyExtension
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

private const val FILE_LOADER = "file-loader"

// language=JavaScript
private fun Project.defaultSettings(): String = """
if (!!config.output) { 
  config.output.chunkFilename = '$jsChunkFileName'
  config.output.clean = true
}
""".trimIndent()

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

private fun cssRules(): String {
    // language=JavaScript
    return """
    config.module.rules = config.module.rules
        .filter(({use}) => !!use)
        .map(rule => ({
          ...rule,
          use: rule.use.map(entry => ({
            ...entry,
            options: {
                esModule: false,
            },
          }))
        }))
    """.trimIndent()
}

class WebpackApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.named<KotlinWebpack>(Webpack.productionTask) {
            outputDirectory.set(getProductionDistDirectory())
        }

        tasks.named<KotlinWebpack>(Webpack.developmentTask) {
            outputDirectory.set(getDevelopmentDistDirectory())
        }

        plugins.apply(SingleWebpackCachePlugin::class)

        tasks.configureEach<PatchWebpackConfig> {
            patch("default-settings", defaultSettings())
            patch("resolve-rules", RESOLVE_RULES)
            patch("font-rules", fontRules())
            patch("css-rules", cssRules())
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

        configurationName(devNpm(FILE_LOADER, "6.2.0"))

        // required for Kotlin/JS CSS support
        configurationName(devNpm("css-loader", "7.1.2"))
        configurationName(devNpm("style-loader", "4.0.0"))
    }
}
