package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

private val MODULE_NAME = StringProperty("kfc.module.name")
private val MODULE_KEEP = StringProperty("kfc.module.keep")
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
    if (!binaries) {
        plugins.apply(WebpackPlugin::class)
    }
    if (distribution || run) {
        plugins.apply(WebComponentPlugin::class)
        plugins.apply(WebpackLoadersPlugin::class)
    }

    val moduleName = getModuleName()
    val moduleKeep = propertyOrNull(MODULE_KEEP)
        ?.takeIf { distribution }
        ?.let { "$moduleName.$it" }

    val fileName = getOutputFileName()

    val kotlin = the<KotlinJsProjectExtension>()
    kotlin.apply {
        js {
            this.moduleName = moduleName

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
                if (moduleKeep != null) {
                    @Suppress("EXPERIMENTAL_API_USAGE")
                    dceTask { keep += moduleKeep }
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
    }

    disableTestsWithoutSources("test")
}

internal fun Project.disableAutomaticJsDistribution() {
    extensions.extraProperties[BUILD_DISTRIBUTION] = false.toString()
}

internal fun Project.disableTestsWithoutSources(
    sourceSetName: String
) {
    tasks.named("${sourceSetName}PackageJson") {
        onlyIf {
            val kotlin = project.extensions.getByName<KotlinProjectExtension>("kotlin")
            val sourceDir = kotlin.sourceSets
                .getByName(sourceSetName)
                .kotlin.sourceDirectories
                .singleOrNull()

            sourceDir?.exists() ?: true
        }
    }
}
