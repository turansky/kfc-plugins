package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.file.*
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.listProperty
import javax.inject.Inject

@CacheableTask
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
        npmProject.dir

    @get:InputDirectory
    private val viteConfigDirectory: DirectoryProperty =
        objectFactory.directoryProperty()
            .convention(projectDir.dir("vite"))

    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val defaultConfigFile: RegularFileProperty

    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    private val configFile: Provider<RegularFile> =
        objectFactory.fileProperty()
            .convention(viteConfigDirectory.file(Vite.CONFIG_FILE))
            .filter { it.asFile.exists() }
            .orElse(defaultConfigFile)

    private val entryFile: Provider<RegularFile> =
        workingDirectory.map { it.file("kotlin/${project.jsModuleName}.mjs") }

    private val envVariables: ListProperty<EnvVariable> =
        objectFactory.listProperty<EnvVariable>()
            .convention(project.bundlerEnvironment.variables)

    private val envFile: RegularFileProperty
        get() = objectFactory.fileProperty()
            .convention { viteEnv(envVariables.get(), entryFile.get()) }

    @TaskAction
    protected fun sync() {
        fs.syncFile(configFile, workingDirectory)
        fs.syncFile(envFile, workingDirectory)

        val additionalFiles = buildSet<String> {
            addAll(getViteConfigFiles(viteConfigDirectory))
            addAll(getViteConfigFiles(workingDirectory))

            remove(configFile.get().asFile.name)
            remove(envFile.get().asFile.name)
        }

        for (fileName in additionalFiles) {
            fs.syncFile(
                source = viteConfigDirectory.file(fileName),
                destination = workingDirectory,
                strategy = SyncFileStrategy.OPTIONAL_SOURCE,
            )
        }
    }
}

private val EXCLUDED_FILES = setOf(
    "package.json",
    "karma.conf.js",
)

private fun getViteConfigFiles(
    directory: Provider<Directory>,
): List<String> {
    val allFiles = directory.get().asFile
        .listFiles { file -> file.isFile }
        ?: return emptyList()

    return allFiles
        .map { it.name }
        .filter { it !in EXCLUDED_FILES }
}
