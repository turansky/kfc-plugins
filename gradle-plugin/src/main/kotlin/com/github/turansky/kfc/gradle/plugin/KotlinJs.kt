package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension

private const val BUILD_DISTRIBUTION = "kotlin.js.generate.executable.default"

internal fun Project.applyKotlinJsPlugin(
    distribution: Boolean = false
) {
    if (!distribution) {
        extensions.extraProperties[BUILD_DISTRIBUTION] = distribution.toString()
    }

    plugins.apply(KotlinPlugin.JS)

    extensions.configure<KotlinJsProjectExtension>("kotlin") {
        js {
            browser()
        }
    }
}
