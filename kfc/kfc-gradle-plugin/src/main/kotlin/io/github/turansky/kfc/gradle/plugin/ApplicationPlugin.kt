package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering

private val WEBPACK_RUN = BooleanProperty("kfc.webpack.run")

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinMultiplatformPlugin(
            distribution = true,
            run = property(WEBPACK_RUN),
        )

        plugins.apply(WebpackBundlePlugin::class)

        val relatedModuleProjects by tasks.registering(RelatedModuleProjects::class)
        val moduleProjects by lazy { relatedModuleProjects.get().calculate() }

        tasks.named(COMPILE_PRODUCTION) {
            moduleProjects.forEach {
                dependsOn(it.tasks.named(COMPILE_PRODUCTION))
            }

            dependsOn(relatedModuleProjects)
        }

        tasks.named(COMPILE_DEVELOPMENT) {
            moduleProjects.forEach {
                dependsOn(it.tasks.named(COMPILE_DEVELOPMENT))
            }

            dependsOn(relatedModuleProjects)
        }

        plugins.apply(SingleWebpackCachePlugin::class)
    }
}
