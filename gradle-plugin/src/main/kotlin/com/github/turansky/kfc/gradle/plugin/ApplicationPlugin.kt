package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

private const val COMPILE_PRODUCTION: String = "compileProductionExecutableKotlinJs"
private const val COMPILE_DEVELOPMENT: String = "compileDevelopmentExecutableKotlinJs"

private const val BPW: String = "browserProductionWebpack"
private const val BDW: String = "browserDevelopmentWebpack"

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
        tasks.named(COMPILE_PRODUCTION) {
            eachRuntimeProjectDependency {
                dependsOn(it.tasks.named(COMPILE_PRODUCTION))
            }
        }

        tasks.named(COMPILE_DEVELOPMENT) {
            eachRuntimeProjectDependency {
                dependsOn(it.tasks.named(COMPILE_DEVELOPMENT))
            }
        }

        tasks.named<KotlinWebpack>(BDW) {
            destinationDirectory = tasks.named<KotlinWebpack>(BPW).get().destinationDirectory
        }
    }

    private fun Project.applyLegacy() {
        val replaceWorker by tasks.registering(Copy::class) {
            eachRuntimeProjectDependency {
                from(it.tasks.getByName(BPW))
            }

            val processDceKotlinJs = tasks.named<KotlinJsDce>("processDceKotlinJs")
            into(processDceKotlinJs.get().destinationDirectory)

            dependsOn(processDceKotlinJs)
        }

        tasks.named(BPW) {
            dependsOn(replaceWorker)
        }
    }
}
