package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFile
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.property
import org.gradle.process.ExecOperations
import org.jetbrains.kotlin.gradle.targets.js.NpmPackageVersion
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrCompilation
import org.jetbrains.kotlin.gradle.targets.js.npm.RequiresNpmDependencies
import org.jetbrains.kotlin.gradle.targets.js.npm.npmProject
import javax.inject.Inject

private val VITE = NpmPackageVersion("vite", "5.4.7")
private const val VITE_BIN = "vite/bin/vite.js"

@CacheableTask
abstract class KotlinViteTask
@Inject constructor(
    objects: ObjectFactory,
    private val execOperations: ExecOperations,
    private val fs: FileSystemOperations,
) : DefaultTask(), RequiresNpmDependencies {

    @Internal
    @Transient
    final override val compilation: KotlinJsIrCompilation =
        project.kotlinJsMainCompilation()

    private val npmProject = compilation.npmProject

    private val buildDirectory =
        compilation.npmProject.dir

    private val sourceMapsProperty: Property<Boolean> = objects.property<Boolean>()
        .convention(project.property(SOURCE_MAPS))

    @Input
    val mode: Property<ViteMode> =
        objects.property<ViteMode>()
            .convention(ViteMode.PRODUCTION)

    private val defaultConfigFile: RegularFileProperty =
        objects.fileProperty()
            .convention(::defaultViteConfig)

    private val customConfigFile: RegularFileProperty =
        objects.fileProperty()
            .convention(project.layout.projectDirectory.file(Vite.configFile))

    private val configFile: RegularFileProperty =
        objects.fileProperty().convention(
            customConfigFile
                .filter { it.asFile.exists() }
                .orElse(defaultConfigFile)
        )

    private val entryFile: Provider<RegularFile> =
        compilation.npmProject.dir
            .map { it.file("kotlin/${project.jsModuleName}.mjs") }

    private val envVariables: ListProperty<EnvVariable> =
        objects.listProperty<EnvVariable>()
            .convention(project.bundlerEnvironment.variables)

    private val envFile: RegularFileProperty =
        objects.fileProperty()
            .convention { viteEnv(envVariables.get(), entryFile.get()) }

    @get:OutputDirectory
    @get:Optional
    abstract val outputDirectory: DirectoryProperty

    @get:Internal
    override val requiredNpmDependencies =
        setOf(VITE)

    @TaskAction
    private fun build() {
        fs.copy {
            from(configFile)
            from(envFile)

            into(buildDirectory)
        }

        val viteArgs = listOf(
            "build",
            "--mode", mode.get().value,
            "--outDir", outputDirectory.get().asFile.absolutePath,
            "--emptyOutDir", "true",
            "--sourcemap", sourceMapsProperty.get().toString()
        )

        execOperations.exec {
            npmProject.useTool(
                exec = this,
                tool = VITE_BIN,
                args = viteArgs,
            )
        }
    }
}
