package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce

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

            val copyYFilesMetamodule = register<Copy>("copyYFilesMetamodule") {
                from(path)
                into(jsPackageDir.resolve("kotlin-dce"))
                include("yfiles.js")
            }

            configureEach<KotlinJsDce> {
                finalizedBy(copyYFilesMetamodule)
            }
        }
    }
}
