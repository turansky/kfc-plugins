package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        disableBrowserTasks()

        plugins.withType<KotlinJsPluginWrapper> {
            plugins.apply(WebpackPlugin::class)

            tasks {
                useModularJsTarget()

                val generateDependencyJson = register<GenerateDependencyJson>("generateDependencyJson")

                named<Jar>(JS_JAR_TASK) {
                    from(generateDependencyJson)
                }
            }
        }
    }
}

