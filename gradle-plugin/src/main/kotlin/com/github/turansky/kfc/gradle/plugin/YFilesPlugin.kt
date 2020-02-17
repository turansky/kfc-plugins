package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

// language=JavaScript
private val EXTERNALS = """
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
      use: ['style-loader', 'css-loader']
    },
    {
      test: /\.svg${'$'}/,
      use: 'svg-inline-loader'
    }
  )
""".trimIndent()

class YFilesPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.withType<WebpackConfigTask> {
            patch("externals", EXTERNALS)
            patch("rules", RULES)
        }
    }
}
