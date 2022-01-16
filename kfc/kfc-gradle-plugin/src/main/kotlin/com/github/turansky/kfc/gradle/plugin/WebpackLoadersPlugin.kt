@file:Suppress("JSLastCommaInObjectLiteral")

package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.targets.js.npm.DevNpmDependencyExtension

private const val CSS_LOADER = "css-loader"
private const val SVG_INLINE_LOADER = "svg-inline-loader"
private const val FILE_LOADER = "file-loader"
private const val WORKER_LOADER = "worker-loader"
private const val STRING_REPLACE_LOADER = "string-replace-loader"

private const val JS_FILE_TEMPLATE = "[name].[contenthash].js"

private val WORKER_FALLBACK = BooleanProperty("kfc.worker.fallback")

// language=JavaScript
private val RULES: String = """
    config.module.rules.push(
      {
        test: /\.css${'$'}/,
        loader: '$CSS_LOADER',
        options: {
          esModule: false,
        },
      },
      {
        test: /\.svg${'$'}/,
        loader: '$SVG_INLINE_LOADER',
      },
    )
""".trimIndent()

private fun Project.fontRules(): String {
    val publicPath = outputPath("/", "fonts")
    val outputPath = outputPath("./", "fonts")

    // language=JavaScript
    return """
    config.module.rules.push( 
      { 
        test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?${'$'}/,
        loader: '$FILE_LOADER',
        options: {
          name: '[name].[contenthash].[ext]',
          publicPath: '$publicPath',
          outputPath: '$outputPath',
          esModule: false
        },
      },
    )
    """.trimIndent()
}

private fun Project.workerRules(): String {
    val fileName = outputPath("/", JS_FILE_TEMPLATE)

    // language=JavaScript
    return """
    const useFallback = !!config.devServer || ${property(WORKER_FALLBACK)}   

    config.module.rules.push( 
      {
        test: /[\.|\-]worker\.js${'$'}/,
        loader: '$WORKER_LOADER',
        options: {
          filename: '$fileName',
          inline: useFallback ? 'fallback' : undefined,  
          esModule: false,
        }
      },
    )
    """.trimIndent()
}

class WebpackLoadersPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(WebpackPlugin::class)

        tasks.configureEach<PatchWebpackConfig> {
            patch("rules", RULES)
            replace("__dynamic_import__", "import")

            patch("font-rules", fontRules())

            if (!project.name.endsWith("-worker"))
                patch("worker-rules", workerRules())
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

        configurationName(devNpm(CSS_LOADER, "6.5.1"))
        configurationName(devNpm(SVG_INLINE_LOADER, "0.8.2"))
        configurationName(devNpm(FILE_LOADER, "6.2.0"))
        configurationName(devNpm(WORKER_LOADER, "3.0.8"))
        configurationName(devNpm(STRING_REPLACE_LOADER, "3.0.3"))
    }
}
