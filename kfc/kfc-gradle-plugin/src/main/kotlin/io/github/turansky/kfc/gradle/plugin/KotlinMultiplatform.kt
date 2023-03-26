package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.applyKotlinMultiplatformPlugin(
    binaries: Boolean = false,
    distribution: Boolean = false,
    run: Boolean = false,
) {
    applyKotlinDefaults()

    plugins.apply(KotlinPlugin.MULTIPLATFORM)

    if (!binaries) {
        plugins.apply(WebpackPlugin::class)
    }
    if (distribution || run) {
        plugins.apply(WebpackLoadersPlugin::class)
    }

    val fileName = jsOutputFileName

    val buildBundle = binaries || distribution || run

    val kotlin = the<KotlinMultiplatformExtension>()
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
            })
            runTask(Action {
                enabled = run
            })
        }

        if (buildBundle) {
            this.binaries.executable()
        }
    }

    configurations.create(JS_MAIN_MODULE)

    tasks {
        configureEach<KotlinJsCompile> {
            kotlinOptions {
                moduleKind = "commonjs"
                freeCompilerArgs += listOf(
                    "-Xgenerate-polyfills=false",
                )
            }
        }
    }
}
