package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

private val WEBPACK_RUN = BooleanProperty("kfc.webpack.run")

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinMultiplatformPlugin(
            distribution = true,
            run = property(WEBPACK_RUN),
        )

        plugins.apply(WebpackBundlePlugin::class)

        link(BUNDLE_PRODUCTION, COMPILE_PRODUCTION)
        link(BUNDLE_DEVELOPMENT, COMPILE_DEVELOPMENT)

        plugins.apply(SingleWebpackCachePlugin::class)
    }
}

internal fun Project.link(
    bundleTask: String,
    compileTask: String,
) {
    tasks.named(bundleTask) {
        eachModuleProjectDependency {
            val compile = it.tasks.named<Kotlin2JsCompile>(compileTask)

            dependsOn(compile)

            inputs.files(compile.get().outputs.files)
        }
    }
}
