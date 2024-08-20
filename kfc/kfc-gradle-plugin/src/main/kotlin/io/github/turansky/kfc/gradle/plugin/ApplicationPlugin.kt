package io.github.turansky.kfc.gradle.plugin

import io.github.turansky.kfc.gradle.plugin.BuildMode.APPLICATION_WEBPACK
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinMultiplatformPlugin(APPLICATION_WEBPACK)

        linkWithModuleCompilation(Webpack.PRODUCTION_TASK, COMPILE_PRODUCTION)
        linkWithModuleCompilation(Webpack.DEVELOPMENT_TASK, COMPILE_DEVELOPMENT)

        plugins.apply(SingleWebpackCachePlugin::class)

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
