package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke

// language=JavaScript
private val RULES = """
    config.module.rules.push(
      {
        test: /\.css${'$'}/,
        use: 'css-loader'
      },
      {
        test: /\.svg${'$'}/,
        use: 'svg-inline-loader'
      }
    )
""".trimIndent()

class YFilesPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks {
            configureEach<PatchWebpackConfig> {
                patch("rules", RULES)
            }
        }
    }
}
