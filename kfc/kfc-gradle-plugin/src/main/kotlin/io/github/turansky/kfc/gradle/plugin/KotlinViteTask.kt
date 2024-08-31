package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.*
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

    @Input
    var mode: ViteMode = ViteMode.DEVELOPMENT

    private val configFile: Provider<File> =
        compilation.npmProject.dir.map { it.file(Vite.configFile).asFile }

    @get:OutputDirectory
    @get:Optional
    abstract val outputDirectory: DirectoryProperty

    @get:Internal
    override val requiredNpmDependencies =
        setOf(VITE)

    @TaskAction
    private fun build() {
        val viteConfig = getViteConfig(project, mode, outputDirectory.get())
        configFile.get().writeText(viteConfig)

        project.exec {
            compilation.npmProject.useTool(
                exec = this,
                tool = VITE_BIN,
                args = listOf("build"),
            )
        }
    }
}
