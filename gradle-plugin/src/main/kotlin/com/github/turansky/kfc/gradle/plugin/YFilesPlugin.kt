package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

// language=JavaScript
private val EXTERNALS = """
    if (config.mode !== 'production') {
      return
    }
    
    config.externals = [
      /^yfiles${'$'}/i,
      /^yfiles\/.+${'$'}/i
    ]
""".trimIndent()

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
        tasks.configureEach<PatchWebpackConfig> {
            patch("externals", EXTERNALS)
            patch("rules", RULES)
        }
    }
}
