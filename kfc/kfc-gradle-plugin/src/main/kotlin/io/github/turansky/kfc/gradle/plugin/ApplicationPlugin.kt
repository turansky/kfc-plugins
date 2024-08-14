package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

private val WEBPACK_RUN = BooleanProperty("kfc.webpack.run")

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinMultiplatformPlugin(
            distribution = true,
            run = property(WEBPACK_RUN),
        )

        plugins.apply(WebpackBundlePlugin::class)

        linkWithModuleCompilation(BUNDLE_PRODUCTION, COMPILE_PRODUCTION)
        linkWithModuleCompilation(BUNDLE_DEVELOPMENT, COMPILE_DEVELOPMENT)

        plugins.apply(SingleWebpackCachePlugin::class)
    }
}

internal fun Project.linkWithModuleCompilation(
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
