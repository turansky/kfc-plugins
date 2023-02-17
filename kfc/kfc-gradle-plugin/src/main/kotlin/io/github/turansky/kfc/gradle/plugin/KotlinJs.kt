package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension

internal fun Project.applyKotlinJsPlugin(
    binaries: Boolean = false,
    distribution: Boolean = false,
    run: Boolean = false,
    singleFile: Boolean = false,
) {
    applyKotlinDefaults(
        both = !binaries && !distribution && !run,
        singleFile = singleFile,
    )

    plugins.apply(KotlinPlugin.JS)
    if (!binaries) {
        plugins.apply(WebpackPlugin::class)
    }
    if (distribution || run) {
        plugins.apply(WebpackLoadersPlugin::class)
    }

    val fileName = jsOutputFileName

    val buildBundle = binaries || distribution || run

    val kotlin = the<KotlinJsProjectExtension>()
    kotlin.js {
        moduleName = jsModuleName

        // TODO: remove redundant `Action` call after migration on Kotlin `1.8.20`
        browser {
            commonWebpackConfig(Action {
                output?.library = null
                outputFileName = fileName
            })
            webpackTask(Action {
                enabled = distribution
                saveEvaluatedConfigFile = false
            })
            runTask(Action {
                enabled = run
                saveEvaluatedConfigFile = false
            })
        }

        if (buildBundle) {
            this.binaries.executable()
        }
    }

    // TODO: remove after migration on multiplatform plugin
    kotlin.apply {
        val mainDir = projectDir.resolve("src/jsMain/kotlin")
        if (mainDir.exists()) {
            sourceSets["main"].kotlin.srcDir(mainDir)
        }

        val testDir = projectDir.resolve("src/jsTest/kotlin")
        if (testDir.exists()) {
            sourceSets["test"].kotlin.srcDir(testDir)
        }
    }

    tasks {
        useModularJsTarget()

        if (binaries) {
            disable<KotlinJsDce>()
        }
    }

    if (buildBundle && !run) {
        gradle.startParameter.excludedTaskNames.add("run")
    }
}
