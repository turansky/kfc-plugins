package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

internal fun Project.applyKotlinMultiplatformPlugin(
    mode: BuildMode,
) {
    applyKotlinDefaults()

    plugins.apply(KotlinPlugin.MULTIPLATFORM)
    plugins.apply(KotlinPlugin.JS_PLAIN_OBJECTS)

    plugins.apply(WebpackPlugin::class)

    if (mode.bundler == Bundler.WEBPACK) {
        plugins.apply(WebpackApplicationPlugin::class)
    }

    val fileName = jsOutputFileName

    val kotlin = the<KotlinMultiplatformExtension>()
    kotlin.js {
        moduleName = jsModuleName

        browser {
            commonWebpackConfig {
                output?.library = null
                outputFileName = fileName
            }

            webpackTask {
                enabled = mode.bundler == Bundler.WEBPACK
            }

            runTask {
                enabled = false
            }
        }

        if (mode.distribution) {
            binaries.executable()
        }
    }

    // `jsMain` source set required
    plugins.apply(AssetsPlugin::class)

    configurations.create(JS_MAIN_MODULE)

    tasks.configureEach<Kotlin2JsCompile> {
        compilerOptions {
            target.set("es2015")

            freeCompilerArgs.addAll(
                "-Xdont-warn-on-error-suppression",
                "-Xgenerate-polyfills=false",
                // TODO: Enable after resolving
                //  https://youtrack.jetbrains.com/issue/KT-67355
                // "-Xir-generate-inline-anonymous-functions",
            )
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
