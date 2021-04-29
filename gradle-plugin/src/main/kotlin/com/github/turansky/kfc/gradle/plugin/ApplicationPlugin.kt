package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinJsPlugin(distribution = true)

        if (jsIrCompiler) {
            applyIr()
        } else {
            applyLegacy()
        }
    }

    private fun Project.applyIr() {
        // Implement
    }

    private fun Project.applyLegacy() {
        // Implement
    }
}
