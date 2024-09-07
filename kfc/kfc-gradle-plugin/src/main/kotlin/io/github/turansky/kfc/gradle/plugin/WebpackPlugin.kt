package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

class WebpackPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.withId(KotlinPlugin.MULTIPLATFORM) {
            val patchWebpackConfig by tasks.registering(PatchWebpackConfig::class) {
                group = DEFAULT_TASK_GROUP

                envVariables.convention(bundlerEnvironment.variables)
            }

            tasks.named<Delete>("clean") {
                delete(patchWebpackConfig)
            }

            tasks.configureEach<KotlinWebpack> {
                dependsOn(patchWebpackConfig)
            }

            tasks.configureEach<KotlinJsTest> {
                dependsOn(patchWebpackConfig)
            }
        }
    }
}
