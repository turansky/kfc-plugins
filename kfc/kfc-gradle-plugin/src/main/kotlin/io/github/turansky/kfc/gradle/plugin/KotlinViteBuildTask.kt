package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.property

@CacheableTask
abstract class KotlinViteBuildTask :
    KotlinViteTask() {

    override val isContinuous = false

    private val sourceMaps: Property<Boolean> =
        objectFactory.property<Boolean>()
            .convention(project.property(SOURCE_MAPS))

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    protected fun build() {
        vite(
            "build",
            "--mode", mode.get().value,
            "--outDir", outputDirectory.get().asFile.absolutePath,
            "--emptyOutDir", "true",
            "--sourcemap", sourceMaps.get().toString()
        )
    }
}
