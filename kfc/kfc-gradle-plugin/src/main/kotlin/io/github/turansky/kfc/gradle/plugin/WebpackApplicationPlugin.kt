@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

private const val CSS_LOADER = "css-loader"
private const val FILE_LOADER = "file-loader"
private const val STYLE_LOADER = "style-loader"

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

// language=JavaScript
private val CSS_RULES: String = """
const options = {
  esModule: false,
}

config.module.rules.push({
  test: /\.css${'$'}/,
  use: [
    {loader: '$STYLE_LOADER', options: options},
    {loader: '$CSS_LOADER', options: options}
  ],
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
            patch("css-rules", CSS_RULES)
            patch("font-rules", fontRules())
        }

        setBundlerDevDependencies(
            BundlerDependency(
                name = CSS_LOADER,
                version = "7.1.2",
            ),
            BundlerDependency(
                name = FILE_LOADER,
                version = "6.2.0",
            ),
            BundlerDependency(
                name = STYLE_LOADER,
                version = "4.0.0",
            ),
        )
    }
}
