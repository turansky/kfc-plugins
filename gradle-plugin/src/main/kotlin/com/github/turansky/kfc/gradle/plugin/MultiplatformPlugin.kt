package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.JvmTarget.JVM_1_8
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

class MultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        disableBrowserTasks()

        plugins.withType<KotlinMultiplatformPluginWrapper> {
            tasks {
                configureEach<KotlinJvmCompile> {
                    kotlinOptions {
                        jvmTarget = JVM_1_8
                    }
                }

                useModularJsTarget()
            }
        }
    }
}
