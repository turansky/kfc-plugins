package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension

internal fun Project.applyKotlinJsPlugin(
    binaries: Boolean = false,
    distribution: Boolean = false,
    run: Boolean = false
) {
    applyKotlinDefaults(!binaries && !distribution && !run)

    plugins.apply(KotlinPlugin.JS)
    if (!binaries) {
        plugins.apply(WebpackPlugin::class)
    }
    if (distribution || run) {
        plugins.apply(WebComponentPlugin::class)
        plugins.apply(WebpackLoadersPlugin::class)
    }

    val moduleKeep = if (distribution) jsModuleKeep else null
    val fileName = jsOutputFileName

    val kotlin = the<KotlinJsProjectExtension>()
    kotlin.apply {
        js {
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
}
