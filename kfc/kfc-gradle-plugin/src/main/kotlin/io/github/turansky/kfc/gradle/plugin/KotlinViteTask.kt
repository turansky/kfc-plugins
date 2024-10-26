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
import org.gradle.deployment.internal.DeploymentRegistry
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.property
import org.gradle.process.internal.ExecHandleFactory
import org.jetbrains.kotlin.gradle.targets.js.NpmPackageVersion
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrCompilation
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProject
import org.jetbrains.kotlin.gradle.targets.js.npm.RequiresNpmDependencies
import org.jetbrains.kotlin.gradle.targets.js.npm.npmProject
import javax.inject.Inject

private val VITE = NpmPackageVersion("vite", "5.4.10")

abstract class KotlinViteTask :
    DefaultTask(),
    RequiresNpmDependencies {

    @get:Inject
    protected abstract val objectFactory: ObjectFactory

    @get:Inject
    protected abstract val fs: FileSystemOperations

    @get:Inject
    protected abstract val execHandleFactory: ExecHandleFactory

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

    @get:Internal
    abstract val isContinuous: Boolean

    protected fun createViteRunner(
        vararg args: String,
    ): BundlerRunner =
        KotlinViteRunner(
            npmProject = npmProject,
            args = args.toList(),
            execHandleFactory = execHandleFactory,
        )

    private fun startNonBlockingViteRunner(runner: BundlerRunner) {
        val deploymentRegistry = services.get(DeploymentRegistry::class.java)
        val deploymentHandle = deploymentRegistry.get(VITE.name, BundlerHandle::class.java)
        if (deploymentHandle == null) {
            deploymentRegistry.start(
                VITE.name,
                DeploymentRegistry.ChangeBehavior.BLOCK,
                BundlerHandle::class.java,
                runner,
            )
        }
    }

    protected fun vite(
        vararg args: String,
    ) {
        fs.copyIfChanged(configFile, workingDirectory)
        fs.copyIfChanged(envFile, workingDirectory)

        val runner = createViteRunner(args = args)

        if (isContinuous) {
            startNonBlockingViteRunner(runner)
        } else {
            runner.start().waitForFinish()
        }
    }
}
