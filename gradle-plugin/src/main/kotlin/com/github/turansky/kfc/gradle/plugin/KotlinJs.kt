package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension

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
    distribution: Boolean = false
) {
    disableAutomaticJsDistribution()

    plugins.apply(KotlinPlugin.JS)

    val fileName = getOutputFileName()

    extensions.configure<KotlinJsProjectExtension>("kotlin") {
        js {
            moduleName = getModuleName()

            browser {
                webpackTask {
                    output.library = null
                    outputFileName = fileName
                }
            }

            if (distribution) {
                binaries.executable()
            }
        }
    }
}

internal fun Project.disableAutomaticJsDistribution() {
    extensions.extraProperties[BUILD_DISTRIBUTION] = false.toString()
}
