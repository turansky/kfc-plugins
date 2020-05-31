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
            "implementation"(npm("css-loader", "^3.5.3"))
            "implementation"(npm("svg-inline-loader", "^0.8.2"))
        }
    }
}
