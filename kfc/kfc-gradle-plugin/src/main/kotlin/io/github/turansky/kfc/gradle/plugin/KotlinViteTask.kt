package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFile
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.property
import org.jetbrains.kotlin.gradle.targets.js.NpmPackageVersion
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrCompilation
import org.jetbrains.kotlin.gradle.targets.js.npm.RequiresNpmDependencies
import org.jetbrains.kotlin.gradle.targets.js.npm.npmProject

private val VITE = NpmPackageVersion("vite", "5.4.5")
private const val VITE_BIN = "vite/bin/vite.js"

@CacheableTask
abstract class KotlinViteTask : DefaultTask(), RequiresNpmDependencies {

    @get:Internal
    final override val compilation: KotlinJsIrCompilation =
        project.kotlinJsMainCompilation()

    @Input
    val mode: Property<ViteMode> =
        project.objects.property<ViteMode>()
            .convention(ViteMode.PRODUCTION)

    private val defaultConfigFile: RegularFileProperty =
        project.objects.fileProperty()
            .convention(::defaultViteConfig)

    private val customConfigFile: RegularFileProperty =
        project.objects.fileProperty()
            .convention(project.layout.projectDirectory.file(Vite.configFile))

    private val configFile: RegularFileProperty =
        project.objects.fileProperty().convention(
            customConfigFile
                .filter { it.asFile.exists() }
                .orElse(defaultConfigFile)
        )

    private val entryFile: Provider<RegularFile> =
        compilation.npmProject.dir
            .map { it.file("kotlin/${project.jsModuleName}.mjs") }

    private val envVariables: ListProperty<EnvVariable> =
        project.objects.listProperty<EnvVariable>()
            .convention(project.bundlerEnvironment.variables)

    private val envFile: RegularFileProperty =
        project.objects.fileProperty()
            .convention { viteEnv(envVariables.get(), entryFile.get()) }

    @get:OutputDirectory
    @get:Optional
    abstract val outputDirectory: DirectoryProperty

    @get:Internal
    override val requiredNpmDependencies =
        setOf(VITE)

    @TaskAction
    private fun build() {
        project.copy {
            from(configFile)
            from(envFile)

            into(compilation.npmProject.dir)
        }

        val viteArgs = listOf(
            "build",
            "--mode", mode.get().toString(),
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
