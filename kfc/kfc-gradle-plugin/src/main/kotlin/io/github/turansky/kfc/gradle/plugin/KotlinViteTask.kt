package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.property
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
    val mode: Property<ViteMode> =
        project.objects.property<ViteMode>()
            .convention(ViteMode.PRODUCTION)

    private val configFile: Provider<File> =
        compilation.npmProject.dir.map { it.file(Vite.configFile).asFile }

    private val envFile: Provider<File> =
        compilation.npmProject.dir.map { it.file(".env").asFile }

    @get:InputFile
    @get:Optional
    val configFileTemplate: File?
        get() = project.layout.projectDirectory.file(Vite.configFile).asFile
            .takeIf { it.exists() }

    @get:OutputDirectory
    @get:Optional
    abstract val outputDirectory: DirectoryProperty

    @get:Internal
    override val requiredNpmDependencies =
        setOf(VITE)

    @TaskAction
    private fun build() {
        val entryFile = compilation.npmProject.dir.get()
            .file("kotlin/${project.jsModuleName}.mjs")

        val viteConfig = getViteConfig(configFileTemplate)
        configFile.get().writeText(viteConfig)

        val bundlerEnvironment = project.extensions.getByName<BundlerEnvironmentExtension>(BUNDLER_ENVIRONMENT)
        val viteEnv = getViteEnv(bundlerEnvironment.variables.get(), entryFile)
        envFile.get().writeText(viteEnv)

        val viteArgs = listOf(
            "build",
            "--mode", mode.toString(),
            "--outDir", outputDirectory.get().asFile.absolutePath,
            "--emptyOutDir", "true",
            "--sourcemap", project.property(SOURCE_MAPS).toString()
        )

        project.exec {
            compilation.npmProject.useTool(
                exec = this,
                tool = VITE_BIN,
                args = viteArgs,
            )
        }
    }
}
