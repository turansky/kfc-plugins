package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

private const val BPW = "browserProductionWebpack"
private const val BDW = "browserDevelopmentWebpack"

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
        val replaceWorker by tasks.registering(Copy::class) {
            for (runtimeProject in project.relatedRuntimeProjects())
                from(runtimeProject.tasks.getByName(BPW))

            val processDceKotlinJs = tasks.named<KotlinJsDce>("processDceKotlinJs")
            into(processDceKotlinJs.get().destinationDirectory)

            dependsOn(processDceKotlinJs)
        }

        tasks.named(BPW) {
            dependsOn(replaceWorker)
        }
    }
}
