package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction

@CacheableTask
abstract class KotlinViteDevTask :
    KotlinViteTask() {

    override val isContinuous =
        project.gradle.startParameter.isContinuous

    @TaskAction
    private fun dev() {
        vite(
            "dev",
            "--mode", mode.get().value,
        )
    }
}
