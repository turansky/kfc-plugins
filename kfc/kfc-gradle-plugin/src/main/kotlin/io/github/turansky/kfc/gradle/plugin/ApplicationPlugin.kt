package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

private val WEBPACK_RUN = BooleanProperty("kfc.webpack.run")

class ApplicationPlugin : Plugin<Project> {
    private val cache = RelatedProjectsCache()

    override fun apply(target: Project): Unit = with(target) {
        applyKotlinMultiplatformPlugin(
            distribution = true,
            run = property(WEBPACK_RUN),
        )

        plugins.apply(WebpackBundlePlugin::class)

        tasks.named(COMPILE_PRODUCTION) {
            eachModuleProjectDependency(cache) {
                dependsOn(it.tasks.named(COMPILE_PRODUCTION))
            }
        }

        tasks.named(COMPILE_DEVELOPMENT) {
            eachModuleProjectDependency(cache) {
                dependsOn(it.tasks.named(COMPILE_DEVELOPMENT))
            }
        }

        plugins.apply(SingleWebpackCachePlugin::class)
    }
}
