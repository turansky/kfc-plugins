package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

class WebpackPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.withId(KotlinPlugin.MULTIPLATFORM) {
            tasks {
                applyConfiguration()
            }
        }
    }

    private fun TaskContainerScope.applyConfiguration() {
        val patchBundlerConfig by registering(PatchBundlerConfig::class)

        named<Delete>("clean") {
            delete(patchBundlerConfig)
        }

        configureEach<KotlinWebpack> {
            dependsOn(patchBundlerConfig)
        }

        configureEach<KotlinJsTest> {
            dependsOn(patchBundlerConfig)
        }
    }
}
