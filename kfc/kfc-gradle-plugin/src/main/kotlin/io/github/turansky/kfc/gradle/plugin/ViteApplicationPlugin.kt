package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create

class ViteApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.create<KotlinViteTask>(Vite.productionTask) {
            group = DEFAULT_TASK_GROUP

            mode.set(ViteMode.PRODUCTION)
            outputDirectory.convention(getProductionDistDirectory())

            dependOnCompile(COMPILE_PRODUCTION)
        }

        tasks.create<KotlinViteTask>(Vite.developmentTask) {
            group = DEFAULT_TASK_GROUP

            mode.set(ViteMode.DEVELOPMENT)
            outputDirectory.convention(getDevelopmentDistDirectory())

            dependOnCompile(COMPILE_DEVELOPMENT)
        }

        plugins.apply(SingleCacheVitePlugin::class)

        tasks.named("build") {
            dependsOn(Vite.productionTask)
        }
    }
}

private fun Task.dependOnCompile(
    taskName: String,
) {
    val compile = project.tasks.named(taskName).get()
    inputs.files(compile.outputs.files)
    dependsOn(compile)
}
