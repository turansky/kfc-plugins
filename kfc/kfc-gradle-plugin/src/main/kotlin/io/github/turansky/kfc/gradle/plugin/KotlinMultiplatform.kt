package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.the
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
    plugins.apply(WebpackPlugin::class)

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
                enabled = mode.bundler == Webpack
            }

            runTask {
                enabled = false
            }
        }

        if (mode.distribution) {
            binaries.executable()
        }
    }

    when (mode.bundler) {
        Webpack,
            -> plugins.apply(WebpackApplicationPlugin::class)

        Vite,
            -> plugins.apply(ViteApplicationPlugin::class)

        null -> {
            // do nothing
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

internal fun Project.kotlinJsMainCompilation(): KotlinJsIrCompilation {
    val kotlin = extensions.getByType<KotlinMultiplatformExtension>()
    val target = kotlin.targets.getByName("js", KotlinJsTargetDsl::class)
    return target.compilations.getByName(KotlinCompilation.MAIN_COMPILATION_NAME)
}
