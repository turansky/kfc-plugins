package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

class ViteApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.create<KotlinViteTask>(Vite.productionTask) {
            group = DEFAULT_TASK_GROUP

            mode.set(ViteMode.PRODUCTION)
            outputDirectory.convention(getProductionDistDirectory())

            dependsOn(COMPILE_PRODUCTION)
        }

        tasks.create<KotlinViteTask>(Vite.developmentTask) {
            group = DEFAULT_TASK_GROUP

            mode.set(ViteMode.DEVELOPMENT)
            outputDirectory.convention(getDevelopmentDistDirectory())

            dependsOn(COMPILE_DEVELOPMENT)
        }

        tasks.named("build") {
            dependsOn(Vite.productionTask)
        }
    }
}
