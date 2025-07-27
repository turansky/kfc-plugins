package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.file.*
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.listProperty
import org.jetbrains.kotlin.gradle.targets.js.NpmPackageVersion
import org.jetbrains.kotlin.gradle.targets.js.npm.RequiresNpmDependencies
import org.jetbrains.kotlin.gradle.targets.js.npm.npmProject
import javax.inject.Inject

abstract class KotlinVitePrepareTask :
    KotlinViteTaskBase(),
    RequiresNpmDependencies {

    @get:Inject
    protected abstract val objectFactory: ObjectFactory

    @get:Inject
    protected abstract val fs: FileSystemOperations

    @get:Inject
    protected abstract val layout: ProjectLayout

    private val workingDirectory: Provider<Directory> =
        compilation.npmProject.dir

    private val defaultConfigFile: RegularFileProperty =
        objectFactory.fileProperty()
            .convention(::defaultViteConfig)

    private val customConfigFile: RegularFileProperty =
        objectFactory.fileProperty()
            .convention(layout.projectDirectory.file(Vite.configFile))

    private val configFile: RegularFileProperty
        get() = objectFactory.fileProperty()
            .convention(
                customConfigFile
                    .filter { it.asFile.exists() }
                    .orElse(defaultConfigFile)
            )

    private val entryFile: Provider<RegularFile> =
        workingDirectory.map { it.file("kotlin/${project.getModuleName(jsPlatform)}.mjs") }

    private val envVariables: ListProperty<EnvVariable> =
        objectFactory.listProperty<EnvVariable>()
            .convention(project.bundlerEnvironment.variables)

    private val envFile: RegularFileProperty
        get() = objectFactory.fileProperty()
            .convention { viteEnv(envVariables.get(), entryFile.get()) }

    @get:Internal
    override val requiredNpmDependencies =
        emptySet<NpmPackageVersion>()

    @TaskAction
    protected fun copy() {
        fs.copyIfChanged(configFile, workingDirectory)
        fs.copyIfChanged(envFile, workingDirectory)
    }
}
