package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.JsModuleKind
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

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

    // `jsMain` source set required
    plugins.apply(AssetsPlugin::class)
    plugins.apply(FixSymlinkPlugin::class)

    configurations.create(JS_MAIN_MODULE)

    tasks.configureEach<Kotlin2JsCompile> {
        compilerOptions {
            moduleKind.set(JsModuleKind.MODULE_ES)
            useEsClasses.set(true)

            freeCompilerArgs.addAll(
                "-Xgenerate-polyfills=false",
                // TODO: Enable after resolving
                //  https://youtrack.jetbrains.com/issue/KT-67355
                // "-Xir-generate-inline-anonymous-functions",
            )

            if (k2mode) {
                freeCompilerArgs.addAll(
                    "-Xdont-warn-on-error-suppression",
                )
            }
        }
    }

    tasks.configureEach<KotlinCompilationTask<*>> {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-Xexpect-actual-classes",
            )
        }
    }
}
