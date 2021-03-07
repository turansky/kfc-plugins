package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.JvmTarget.JVM_1_8
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

private const val NO_WARN: String = "kotlin.mpp.stability.nowarn"
private const val GRANULAR_SOURCE_SETS: String = "kotlin.mpp.enableGranularSourceSetsMetadata"

class MultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        ext(NO_WARN, true)
        ext(GRANULAR_SOURCE_SETS, true)

        disableAutomaticJsDistribution()

        plugins.apply(KotlinPlugin.MULTIPLATFORM)

        extensions.configure<KotlinMultiplatformExtension>("kotlin") {
            jvm()
            js {
                browser()
            }
        }

        tasks {
            configureEach<KotlinJvmCompile> {
                kotlinOptions {
                    jvmTarget = JVM_1_8
                }
            }

            useModularJsTarget()
        }

        disableTestsWithoutSources("jsTest")
    }
}
