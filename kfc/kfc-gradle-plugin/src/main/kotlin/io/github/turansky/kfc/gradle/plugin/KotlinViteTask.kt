package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFile
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.property
import org.gradle.process.ExecOperations
import org.jetbrains.kotlin.gradle.targets.js.NpmPackageVersion
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrCompilation
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProject
import org.jetbrains.kotlin.gradle.targets.js.npm.RequiresNpmDependencies
import org.jetbrains.kotlin.gradle.targets.js.npm.npmProject
import javax.inject.Inject

private val VITE = NpmPackageVersion("vite", "5.4.8")
private const val VITE_BIN = "vite/bin/vite.js"

abstract class KotlinViteTask :
    DefaultTask(),
    RequiresNpmDependencies {

    @get:Inject
    protected abstract val objectFactory: ObjectFactory

    @get:Inject
    protected abstract val execOperations: ExecOperations

    @get:Inject
    protected abstract val fileSystemOperations: FileSystemOperations

    @Internal
    @Transient
    final override val compilation: KotlinJsIrCompilation =
        project.kotlinJsMainCompilation()

    private val npmProject: NpmProject =
        compilation.npmProject

    private val workingDirectory: Provider<Directory> =
        npmProject.dir

    @Input
    val mode: Property<ViteMode> =
        objectFactory.property<ViteMode>()
            .convention(ViteMode.PRODUCTION)

    private val defaultConfigFile: RegularFileProperty =
        objectFactory.fileProperty()
            .convention(::defaultViteConfig)

    private val customConfigFile: RegularFileProperty =
        objectFactory.fileProperty()
            .convention(project.layout.projectDirectory.file(Vite.configFile))

    private val configFile: RegularFileProperty =
        objectFactory.fileProperty().convention(
            customConfigFile
                .filter { it.asFile.exists() }
                .orElse(defaultConfigFile)
        )

    private val entryFile: Provider<RegularFile> =
        workingDirectory.map { it.file("kotlin/${project.jsModuleName}.mjs") }

    private val envVariables: ListProperty<EnvVariable> =
        objectFactory.listProperty<EnvVariable>()
            .convention(project.bundlerEnvironment.variables)

    private val envFile: RegularFileProperty =
        objectFactory.fileProperty()
            .convention { viteEnv(envVariables.get(), entryFile.get()) }

    @get:Internal
    override val requiredNpmDependencies =
        setOf(VITE)

    protected fun vite(
        vararg args: String,
    ) {
        fileSystemOperations.copy {
            from(configFile)
            from(envFile)

            into(workingDirectory)
        }

        execOperations.exec {
            npmProject.useTool(
                exec = this,
                tool = VITE_BIN,
                args = args.toList(),
            )
        }
    }
}
