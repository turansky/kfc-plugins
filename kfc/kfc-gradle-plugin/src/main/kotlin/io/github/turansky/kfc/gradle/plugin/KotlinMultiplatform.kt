package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.Mode

internal fun Project.applyKotlinMultiplatformPlugin(
    binaries: Boolean = false,
    distribution: Boolean = false,
    run: Boolean = false,
    singleFile: Boolean = false,
) {
    applyKotlinDefaults(
        both = !binaries && !distribution && !run,
        singleFile = singleFile,
    )

    plugins.apply(KotlinPlugin.MULTIPLATFORM)

    if (!binaries) {
        plugins.apply(WebpackPlugin::class)
    }
    if (distribution || run) {
        plugins.apply(WebpackLoadersPlugin::class)
    }

    val productionFileName = getJsOutputFileName(production = true)
    val developmentFileName = getJsOutputFileName(production = false)

    val buildBundle = binaries || distribution || run

    val kotlin = the<KotlinMultiplatformExtension>()
    kotlin.js {
        moduleName = jsModuleName

        // TODO: remove redundant `Action` call after migration on Kotlin `1.8.20`
        browser {
            commonWebpackConfig(Action {
                output?.library = null
            })
            webpackTask(Action {
                enabled = distribution
                outputFileName = when (mode) {
                    Mode.DEVELOPMENT -> developmentFileName
                    Mode.PRODUCTION -> productionFileName
                }
            })
            runTask(Action {
                enabled = run
            })
        }

        if (buildBundle) {
            this.binaries.executable()
        }
    }

    tasks {
        useModularJsTarget()

        if (binaries) {
            disable<KotlinJsDce>()
        }
    }
}
