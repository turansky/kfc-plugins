package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.create

private const val ROLLUP_PLUGIN_SOURCEMAPS = "rollup-plugin-sourcemaps"

class ViteApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.create<KotlinViteBuildTask>(Vite.productionTask) {
            group = DEFAULT_TASK_GROUP

            mode.set(ViteMode.PRODUCTION)
            outputDirectory.convention(getProductionDistDirectory())

            dependOnCompile(COMPILE_PRODUCTION)
        }

        tasks.create<KotlinViteBuildTask>(Vite.developmentTask) {
            group = DEFAULT_TASK_GROUP

            mode.set(ViteMode.DEVELOPMENT)
            outputDirectory.convention(getDevelopmentDistDirectory())

            dependOnCompile(COMPILE_DEVELOPMENT)
        }

        tasks.named("build") {
            dependsOn(Vite.productionTask)
        }

        tasks.create<KotlinViteDevTask>(Vite.runTask) {
            group = DEFAULT_TASK_GROUP

            mode.set(ViteMode.DEVELOPMENT)

            dependOnCompile(COMPILE_DEVELOPMENT)
        }

        setBundlerDevDependencies(
            BundlerDependency(
                name = ROLLUP_PLUGIN_SOURCEMAPS,
                version = "0.6.3",
            ),
        )
    }
}

private fun Task.dependOnCompile(
    taskName: String,
) {
    val compile = project.tasks.named(taskName).get()
    inputs.files(compile.outputs.files)
    dependsOn(compile)
}
