package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinJsPlugin()
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
