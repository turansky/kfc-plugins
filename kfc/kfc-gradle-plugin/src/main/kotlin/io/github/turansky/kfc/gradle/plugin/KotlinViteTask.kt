package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.jetbrains.kotlin.gradle.targets.js.NpmPackageVersion
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrCompilation
import org.jetbrains.kotlin.gradle.targets.js.npm.RequiresNpmDependencies
import org.jetbrains.kotlin.gradle.targets.js.npm.npmProject
import java.io.File

private val VITE = NpmPackageVersion("vite", "5.4.2")
private const val VITE_BIN = "vite/bin/vite.js"

abstract class KotlinViteTask : DefaultTask(), RequiresNpmDependencies {

    @get:Internal
    final override val compilation: KotlinJsIrCompilation =
        project.kotlinJsMainCompilation()

    @get:OutputFile
    open val configFile: Provider<File> =
        compilation.npmProject.dir.map { it.file("vite.config.js").asFile }

    @get:Internal
    override val requiredNpmDependencies =
        setOf(VITE)

    @TaskAction
    private fun build() {
        configFile.get().writeText(getViteConfig(project))

        project.exec {
            compilation.npmProject.useTool(
                exec = this,
                tool = VITE_BIN,
                args = listOf("build"),
            )
        }
    }
}
