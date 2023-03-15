package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

private val WEBPACK_RUN = BooleanProperty("kfc.webpack.run")

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinMultiplatformPlugin(
            distribution = true,
            run = property(WEBPACK_RUN),
        )

        plugins.apply(WebpackBundlePlugin::class)

        if (jsIrCompiler) {
            applyIr()
        } else {
            applyLegacy()
        }
    }

    private fun Project.applyIr() {
        tasks.named(COMPILE_PRODUCTION) {
            eachRuntimeProjectDependency {
                dependsOn(it.tasks.named(COMPILE_PRODUCTION))
            }
        }

        tasks.named(COMPILE_DEVELOPMENT) {
            eachRuntimeProjectDependency {
                dependsOn(it.tasks.named(COMPILE_DEVELOPMENT))
            }
        }

        plugins.apply(SingleWebpackCachePlugin::class)
    }

    private fun Project.applyLegacy() {
        val replaceWorker by tasks.registering(Copy::class) {
            eachRuntimeProjectDependency {
                from(it.tasks.named(Webpack.PRODUCTION_TASK))
            }

            val processDceKotlinJs = tasks.named<KotlinJsDce>("processDceJsKotlinJs")
            into(processDceKotlinJs.get().destinationDirectory)

            dependsOn(processDceKotlinJs)
        }

        tasks.named(Webpack.PRODUCTION_TASK) {
            dependsOn(replaceWorker)
        }
    }
}
