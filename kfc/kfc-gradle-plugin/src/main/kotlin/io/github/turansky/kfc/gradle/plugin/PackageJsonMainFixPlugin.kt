package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

// WA: For ESM bundles there's incorrect package.json "main" field defined
class PackageJsonMainFixPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val kotlin = the<KotlinMultiplatformExtension>()

        kotlin.js().compilations.forEach {
            it.packageJson {
                val mainField = main?.removeSuffix(".js")

                if (!mainField.isNullOrEmpty() && !mainField.endsWith(".mjs")) {
                    this.main = "$mainField.mjs"
                }
            }
        }
    }
}