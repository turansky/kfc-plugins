package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

private val WEBPACK_RUN = BooleanProperty("kfc.webpack.run")

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinMultiplatformPlugin(
            distribution = true,
            run = property(WEBPACK_RUN),
        )

        plugins.apply(WebpackBundlePlugin::class)

        tasks.named(WEBPACK_PRODUCTION) {
            linkToOutputOf<Kotlin2JsCompile>(
                task = COMPILE_PRODUCTION
            )
        }

        tasks.named(WEBPACK_DEVELOPMENT) {
            linkToOutputOf<Kotlin2JsCompile>(
                task = COMPILE_DEVELOPMENT
            )
        }

        plugins.apply(SingleWebpackCachePlugin::class)
    }
}
