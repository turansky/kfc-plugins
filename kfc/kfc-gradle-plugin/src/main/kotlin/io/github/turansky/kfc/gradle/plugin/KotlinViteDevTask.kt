package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction

@CacheableTask
abstract class KotlinViteDevTask :
    KotlinViteTask() {

    @TaskAction
    private fun dev() {
        vite(
            "dev",
            "--mode", mode.get().value,
        )
    }
}
