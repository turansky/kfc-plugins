package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmDependencyExtension

private const val CSS_LOADER = "css-loader"
private const val SVG_INLINE_LOADER = "svg-inline-loader"

private const val IMPLEMENTATION = "implementation"

// language=JavaScript
private val RULES = """
    config.module.rules.push(
      {
        test: /\.css${'$'}/,
        use: '$CSS_LOADER'
      },
      {
        test: /\.svg${'$'}/,
        use: '$SVG_INLINE_LOADER'
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
                from(project.projectDir)
                into(jsPackageDir("kotlin-dce"))
                include("yfiles.js")
            }

            configureEach<KotlinJsDce> {
                finalizedBy(copyYFilesMetamodule)
            }
        }

        val npm = dependencies.extensions.getByName<NpmDependencyExtension>("npm")

        dependencies {
            IMPLEMENTATION(npm(CSS_LOADER, "3.5.3"))
            IMPLEMENTATION(npm(SVG_INLINE_LOADER, "0.8.2"))
        }
    }
}
