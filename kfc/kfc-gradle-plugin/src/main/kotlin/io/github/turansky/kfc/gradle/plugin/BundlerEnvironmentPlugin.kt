package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

class BundlerEnvironmentPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        extensions.create<BundlerEnvironmentExtension>("bundlerEnvironment")
    }
}
