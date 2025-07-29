package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.file.*
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.listProperty
import org.jetbrains.kotlin.gradle.targets.js.npm.npmProject
import javax.inject.Inject

private val DOT_ENV_FILES = setOf(
    DOT_ENV,
    DOT_ENV_PRODUCTION,
    DOT_ENV_DEVELOPMENT,
)

abstract class KotlinVitePrepareTask :
    KotlinViteTaskBase() {

    @get:Inject
    protected abstract val objectFactory: ObjectFactory

    @get:Inject
    protected abstract val fs: FileSystemOperations

    @get:Inject
    protected abstract val layout: ProjectLayout

    private val projectDir: DirectoryProperty
        get() = objectFactory.directoryProperty()
            .convention(layout.projectDirectory)

    private val workingDirectory: Provider<Directory> =
        compilation.npmProject.dir

    private val defaultConfigFile: RegularFileProperty =
        objectFactory.fileProperty()
            .convention(::defaultViteConfig)

    private val customConfigFile: RegularFileProperty =
        objectFactory.fileProperty()
            .convention(projectDir.file(Vite.configFile))

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

    @TaskAction
    protected fun copy() {
        fs.copyIfChanged(configFile, workingDirectory)
        fs.copyIfChanged(envFile, workingDirectory)

        for (fileName in DOT_ENV_FILES) {
            val file = projectDir.file(fileName)

            if (file.get().asFile.isFile) {
                fs.copyIfChanged(file, workingDirectory)
            } else {
                fs.delete {
                    delete(workingDirectory.map { it.file(fileName) })
                }
            }
        }
    }
}
