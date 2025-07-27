package io.github.turansky.kfc.gradle.plugin

import io.github.turansky.kfc.gradle.plugin.BuildMode.APPLICATION
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.register

class ViteApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val bundler = requireNotNull(APPLICATION.bundler)

        if (kfcPlatform.js) {
            addBundlerTasks(bundler.js)
        }

        if (kfcPlatform.wasmJs) {
            addBundlerTasks(bundler.wasmJs)
        }
    }

    private fun Project.addBundlerTasks(
        configuration: BundlerConfiguration,
    ) {
        tasks.register<KotlinVitePrepareTask>(configuration.development.prepareTask) {
            group = DEFAULT_TASK_GROUP

            dependsOn(configuration.development.compileSyncTask)
        }

        tasks.register<KotlinVitePrepareTask>(configuration.production.prepareTask) {
            group = DEFAULT_TASK_GROUP

            dependsOn(configuration.production.compileSyncTask)
        }

        tasks.register<KotlinViteBuildTask>(configuration.production.buildTask) {
            group = DEFAULT_TASK_GROUP

            dependsOn(configuration.production.prepareTask)

            mode.set(ViteMode.PRODUCTION)
            outputDirectory.convention(getProductionDistDirectory(configuration.platform))

            dependOnCompile(COMPILE_PRODUCTION)
        }

        tasks.register<KotlinViteBuildTask>(configuration.development.buildTask) {
            group = DEFAULT_TASK_GROUP

            dependsOn(configuration.development.prepareTask)

            mode.set(ViteMode.DEVELOPMENT)
            outputDirectory.convention(getDevelopmentDistDirectory(configuration.platform))

            dependOnCompile(COMPILE_DEVELOPMENT)
        }

        tasks.named("build") {
            dependsOn(configuration.production.buildTask)
        }

        tasks.register<KotlinViteDevTask>(configuration.runTask) {
            group = DEFAULT_TASK_GROUP

            dependsOn(configuration.development.prepareTask)

            mode.set(ViteMode.DEVELOPMENT)

            dependOnCompile(COMPILE_DEVELOPMENT)
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
