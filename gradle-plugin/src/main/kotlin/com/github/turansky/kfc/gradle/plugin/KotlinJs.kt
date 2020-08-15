package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension

internal fun Project.applyKotlinJsPlugin() {
    plugins.apply(KotlinPlugin.JS)

    extensions.configure<KotlinJsProjectExtension>("kotlin") {
        js {
            browser()
        }
    }
}
