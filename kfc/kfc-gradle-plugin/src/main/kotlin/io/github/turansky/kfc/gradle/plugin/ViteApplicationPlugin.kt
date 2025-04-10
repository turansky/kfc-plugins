package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.register

private const val ROLLUP_PLUGIN_SOURCEMAPS = "rollup-plugin-sourcemaps"

class ViteApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.register<KotlinVitePreparationTask>(Vite.developmentPreparationTask) {
            group = DEFAULT_TASK_GROUP

            dependsOn("jsDevelopmentExecutableCompileSync")
        }
        tasks.register<KotlinVitePreparationTask>(Vite.productionPreparationTask) {
            group = DEFAULT_TASK_GROUP

            dependsOn("jsProductionExecutableCompileSync")
        }

        tasks.register<KotlinViteBuildTask>(Vite.productionTask) {
            group = DEFAULT_TASK_GROUP

            dependsOn(Vite.productionPreparationTask)

            mode.set(ViteMode.PRODUCTION)
            outputDirectory.convention(getProductionDistDirectory())

            dependOnCompile(COMPILE_PRODUCTION)
        }

        tasks.register<KotlinViteBuildTask>(Vite.developmentTask) {
            group = DEFAULT_TASK_GROUP

            dependsOn(Vite.developmentPreparationTask)

            mode.set(ViteMode.DEVELOPMENT)
            outputDirectory.convention(getDevelopmentDistDirectory())

            dependOnCompile(COMPILE_DEVELOPMENT)
        }

        tasks.named("build") {
            dependsOn(Vite.productionTask)
        }

        tasks.register<KotlinViteDevTask>(Vite.runTask) {
            group = DEFAULT_TASK_GROUP

            dependsOn(Vite.developmentPreparationTask)

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
