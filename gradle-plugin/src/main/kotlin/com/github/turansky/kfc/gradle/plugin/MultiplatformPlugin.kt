package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

private const val NO_WARN: String = "kotlin.mpp.stability.nowarn"

private val JS_ONLY = BooleanProperty("kfc.js.only")

class MultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        ext(NO_WARN, true)

        applyKotlinDefaults(true)

        plugins.apply(KotlinPlugin.MULTIPLATFORM)

        extensions.configure<KotlinMultiplatformExtension>("kotlin") {
            if (!property(JS_ONLY)) {
                jvm()
            }

            js {
                browser()
            }
        }

        tasks {
            useModularJsTarget()
        }
    }
}
