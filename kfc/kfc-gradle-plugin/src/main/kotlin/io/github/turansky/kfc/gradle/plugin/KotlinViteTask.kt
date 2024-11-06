package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.*
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.*
import org.gradle.deployment.internal.DeploymentRegistry
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.property
import org.gradle.process.ExecOperations
import org.gradle.process.internal.ExecHandleFactory
import org.gradle.work.NormalizeLineEndings
import org.jetbrains.kotlin.gradle.targets.js.NpmPackageVersion
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrCompilation
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProject
import org.jetbrains.kotlin.gradle.targets.js.npm.RequiresNpmDependencies
import org.jetbrains.kotlin.gradle.targets.js.npm.npmProject
import javax.inject.Inject

private val VITE = NpmPackageVersion("vite", "5.4.11")

abstract class KotlinViteTask :
    DefaultTask(),
    RequiresNpmDependencies {

    @get:Inject
    protected abstract val objectFactory: ObjectFactory

    @get:Inject
    protected abstract val fs: FileSystemOperations

    @get:Inject
    protected abstract val layout: ProjectLayout

    @get:Inject
    protected abstract val execHandleFactory: ExecHandleFactory

    @get:Inject
    protected abstract val execOperations: ExecOperations

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
            .convention(layout.projectDirectory.file(Vite.configFile))

    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:NormalizeLineEndings
    protected val configFile: RegularFileProperty
        get() = objectFactory.fileProperty()
            .convention(
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
            execOperations = execOperations,
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
        fs.copy {
            from(
                configFile,
                envFile,
            )

            into(workingDirectory)
        }

        val runner = createViteRunner(args = args)

        if (isContinuous) {
            startNonBlockingViteRunner(runner)
        } else {
            runner.execute()
        }
    }
}
