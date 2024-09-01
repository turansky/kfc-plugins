package io.github.turansky.kfc.gradle.plugin

import io.github.turansky.kfc.gradle.plugin.BuildMode.APPLICATION
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val bundler = getBundler()

        applyKotlinMultiplatformPlugin(APPLICATION(bundler))

        val productionTask = if (bundler == Webpack) Webpack.productionTask else Vite.productionTask
        val developmentTask = if (bundler == Webpack) Webpack.developmentTask else Vite.developmentTask

        linkWithModuleCompilation(productionTask, COMPILE_PRODUCTION)
        linkWithModuleCompilation(developmentTask, COMPILE_DEVELOPMENT)

        plugins.apply(BundlePlugin::class)
    }
}

private fun Project.linkWithModuleCompilation(
    bundleTask: String,
    compileTask: String,
) {
    tasks.named(bundleTask) {
        eachModuleProjectDependency {
            val compile = it.tasks.getByName(compileTask)

            dependsOn(compile)

            inputs.files(compile.outputs.files)
        }
    }
}
