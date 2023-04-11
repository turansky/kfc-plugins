package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

private val ES_MODE = BooleanProperty("kfc.es.mode", true)

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

        if (buildBundle) {
            this.binaries.executable()
        }
    }

    configurations.create(JS_MAIN_MODULE)

    val esMode = project.property(ES_MODE)

    tasks {
        configureEach<KotlinJsCompile> {
            kotlinOptions {
                moduleKind = if (esMode) "es" else "commonjs"
                freeCompilerArgs += listOf(
                    "-Xgenerate-polyfills=false",
                )
                useEsClasses = esMode
            }
        }
    }
}
