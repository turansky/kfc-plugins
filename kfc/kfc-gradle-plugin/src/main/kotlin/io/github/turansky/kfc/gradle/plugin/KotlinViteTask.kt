package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.jetbrains.kotlin.gradle.targets.js.NpmPackageVersion
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrCompilation
import org.jetbrains.kotlin.gradle.targets.js.npm.RequiresNpmDependencies
import org.jetbrains.kotlin.gradle.targets.js.npm.npmProject

private val VITE = NpmPackageVersion("vite", "5.4.2")

abstract class KotlinViteTask : DefaultTask(), RequiresNpmDependencies {

    @get:Internal
    override val compilation: KotlinJsIrCompilation =
        project.kotlinJsMainCompilation()

    @get:Internal
    override val requiredNpmDependencies =
        setOf(VITE)

    @TaskAction
    private fun build() {
        project.exec {
            compilation.npmProject.useTool(
                this,
                "vite",
                args = listOf(
                    "build",
                )
            )
        }
    }
}
