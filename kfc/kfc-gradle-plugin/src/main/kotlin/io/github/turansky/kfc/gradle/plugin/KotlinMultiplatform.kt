@file:OptIn(
    ExperimentalWasmDsl::class,
)

package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrCompilation
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

internal fun Project.applyKotlinMultiplatformPlugin(
    mode: BuildMode,
) {
    applyKotlinDefaults()

    plugins.apply(KotlinPlugin.MULTIPLATFORM)
    plugins.apply(KotlinPlugin.JS_PLAIN_OBJECTS)
    plugins.apply(LatestToolsPlugin::class)

    plugins.apply(BundlerEnvironmentPlugin::class)

    configure<KotlinMultiplatformExtension> {
        compilerOptions {
            optIn.add("kotlin.ExperimentalStdlibApi")
        }

        js {
            outputModuleName.set(jsModuleName)

            // TODO: Remove
            browser {
                webpackTask {
                    enabled = false
                }

                runTask {
                    enabled = false
                }
            }

            if (mode.bundler != null) {
                binaries.executable()
            }
        }

        if (kfcPlatform.wasmJs && mode.bundler == null) {
            wasmJs {
                outputModuleName.set(jsModuleName)

                browser()
            }
        }
    }

    when (mode.bundler) {
        Vite -> plugins.apply(ViteApplicationPlugin::class)
        null -> Unit
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
                "-Xir-generate-inline-anonymous-functions",
                "-Xwarning-level=NOTHING_TO_INLINE:disabled",
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

internal fun Project.kotlinJsMainCompilation(): KotlinJsIrCompilation {
    val kotlin = extensions.getByType<KotlinMultiplatformExtension>()
    val target = kotlin.targets.getByName("js", KotlinJsTargetDsl::class)
    return target.compilations.getByName(KotlinCompilation.MAIN_COMPILATION_NAME)
}
