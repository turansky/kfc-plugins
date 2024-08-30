package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.jetbrains.kotlin.gradle.targets.js.NpmPackageVersion
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrCompilation
import org.jetbrains.kotlin.gradle.targets.js.npm.RequiresNpmDependencies
import org.jetbrains.kotlin.gradle.targets.js.npm.npmProject

private val VITE = NpmPackageVersion("vite", "5.4.2")
private const val VITE_BIN = "vite/bin/vite.js"

abstract class KotlinViteTask : DefaultTask(), RequiresNpmDependencies {

    @get:Internal
    override val compilation: KotlinJsIrCompilation =
        project.kotlinJsMainCompilation()

    @get:Internal
    override val requiredNpmDependencies =
        setOf(VITE)

    @TaskAction
    private fun build() {
        compilation.npmProject.dir.get()
            .file("vite.config.js").asFile
            .writeText(getViteConfig(project))

        project.exec {
            compilation.npmProject.useTool(
                exec = this,
                tool = VITE_BIN,
                args = listOf("build"),
            )
        }
    }
}
