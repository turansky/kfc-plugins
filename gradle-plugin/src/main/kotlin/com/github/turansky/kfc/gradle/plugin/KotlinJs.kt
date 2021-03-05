package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension

private const val BUILD_DISTRIBUTION = "kotlin.js.generate.executable.default"

internal fun Project.applyKotlinJsPlugin(
    distribution: Boolean = false
) {
    disableAutomaticJsDistribution()

    plugins.apply(KotlinPlugin.JS)

    extensions.configure<KotlinJsProjectExtension>("kotlin") {
        js {
            browser {
                webpackTask {
                    output.library = null
                }
            }

            if (distribution) {
                binaries.executable()
            }
        }
    }
}

internal fun Project.disableAutomaticJsDistribution() {
    extensions.extraProperties[BUILD_DISTRIBUTION] = false.toString()
}
