package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import javax.inject.Inject

@DisableCachingByDefault
abstract class KotlinViteDefaultConfigTask :
    DefaultTask() {

    @get:Inject
    protected abstract val objectFactory: ObjectFactory

    private val defaultOutputFile: RegularFileProperty =
        objectFactory.fileProperty()
            .fileValue(temporaryDir.resolve(Vite.CONFIG_FILE))

    @get:OutputFile
    val configFile: RegularFileProperty =
        objectFactory.fileProperty()
            .convention(defaultOutputFile)

    @TaskAction
    protected fun create() {
        configFile.asFile.get().writeText(DEFAULT_VITE_CONFIG)
    }
}
