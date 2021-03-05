package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

class WorkerPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinJsPlugin(binaries = true)

        tasks {
            useModularJsTarget()

            disable<KotlinJsDce>()
            disable<KotlinWebpack>()
        }
    }
}
