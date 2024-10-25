package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinPackageJsonTask

class FixModuleTypePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.configureEach<KotlinPackageJsonTask> {
            packageJsonHandlers.add {
                customField("type", "module")
            }
        }
    }
}
