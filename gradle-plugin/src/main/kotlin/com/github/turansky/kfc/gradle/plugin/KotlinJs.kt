package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import java.io.File

private val MODULE_NAME = StringProperty("kfc.module.name")
private val OUTPUT_NAME = StringProperty("kfc.output.name")

private const val BUILD_DISTRIBUTION = "kotlin.js.generate.executable.default"

private fun Project.getModuleName(): String {
    propertyOrNull(MODULE_NAME)
        ?.let { return it }

    return when (this) {
        rootProject -> rootProject.name
        else -> "${rootProject.name}-$name"
    }
}

private fun Project.getOutputFileName(): String {
    val name = propertyOrNull(OUTPUT_NAME)
        ?: getModuleName()

    return "$name.js"
}

internal fun Project.applyKotlinJsPlugin(
    binaries: Boolean = false,
    distribution: Boolean = false,
    run: Boolean = false
) {
    disableAutomaticJsDistribution()

    plugins.apply(KotlinPlugin.JS)

    val fileName = getOutputFileName()

    val kotlin = the<KotlinJsProjectExtension>()
    kotlin.apply {
        js {
            moduleName = getModuleName()

            browser {
                commonWebpackConfig {
                    output?.library = null
                    outputFileName = fileName
                }
                webpackTask {
                    enabled = distribution
                }
                runTask {
                    enabled = run
                }
            }

            if (binaries || distribution || run) {
                this.binaries.executable()
            }
        }
    }

    tasks {
        useModularJsTarget()

        if (binaries) {
            disable<KotlinJsDce>()
        }

        named("testPackageJson") {
            onlyIf {
                val sourceDir = kotlin.singleSourceDir("test")
                sourceDir?.exists() ?: true
            }
        }
    }
}

internal fun Project.disableAutomaticJsDistribution() {
    extensions.extraProperties[BUILD_DISTRIBUTION] = false.toString()
}

private fun KotlinJsProjectExtension.singleSourceDir(
    sourceSetName: String
): File? =
    sourceSets.getByName(sourceSetName)
        .kotlin.sourceDirectories.singleOrNull()
