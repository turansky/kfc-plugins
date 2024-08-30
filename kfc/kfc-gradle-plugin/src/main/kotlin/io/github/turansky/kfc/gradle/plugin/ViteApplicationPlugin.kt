package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

class ViteApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.create<KotlinViteTask>(Vite.productionTask) {
            outputDirectory.convention(getProductionDistDirectory())

            dependsOn(COMPILE_PRODUCTION)
        }

        tasks.create<KotlinViteTask>(Vite.developmentTask) {
            outputDirectory.convention(getDevelopmentDistDirectory())

            dependsOn(COMPILE_DEVELOPMENT)
        }
    }
}
