package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

private val WEBPACK_RUN = BooleanProperty("kfc.webpack.run")
private val REACT_LAZY = BooleanProperty("kfc.react.lazy")

private const val BPW: String = "browserProductionWebpack"
private const val BDW: String = "browserDevelopmentWebpack"

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinJsPlugin(
            distribution = true,
            run = property(WEBPACK_RUN),
        )

        if (property(REACT_LAZY)) {
            plugins.apply(ReactPlugin::class)
        }

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

        tasks.named<KotlinWebpack>(BDW) {
            destinationDirectory = tasks.named<KotlinWebpack>(BPW).get().destinationDirectory
        }
    }

    private fun Project.applyLegacy() {
        val replaceWorker by tasks.registering(Copy::class) {
            eachRuntimeProjectDependency {
                from(it.tasks.getByName(BPW))
            }

            val processDceKotlinJs = tasks.named<KotlinJsDce>("processDceKotlinJs")
            into(processDceKotlinJs.get().destinationDirectory)

            dependsOn(processDceKotlinJs)
        }

        tasks.named(BPW) {
            dependsOn(replaceWorker)
        }
    }
}
