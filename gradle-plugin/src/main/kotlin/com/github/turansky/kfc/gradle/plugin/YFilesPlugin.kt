package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.targets.js.npm.DevNpmDependencyExtension

private const val CSS_LOADER = "css-loader"
private const val SVG_INLINE_LOADER = "svg-inline-loader"

private const val IMPLEMENTATION = "implementation"

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

class YFilesPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.configureEach<PatchWebpackConfig> {
            patch("rules", RULES)
        }

        val devNpm = dependencies.extensions
            .getByName<DevNpmDependencyExtension>("devNpm")

        dependencies {
            IMPLEMENTATION(devNpm(CSS_LOADER, "4.3.0"))
            IMPLEMENTATION(devNpm(SVG_INLINE_LOADER, "0.8.2"))
        }
    }
}
