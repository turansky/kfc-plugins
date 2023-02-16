package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

private const val NO_WARN: String = "kotlin.mpp.stability.nowarn"

class MultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        rootProject.ext(NO_WARN, true)

        applyKotlinDefaults(
            both = true,
        )

        plugins.apply(KotlinPlugin.MULTIPLATFORM)
        plugins.apply(LatestNodePlugin::class)

        extensions.configure<KotlinMultiplatformExtension>("kotlin") {
            js {
                browser()
            }
        }

        tasks {
            useModularJsTarget()
        }
    }
}
