package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByName

private const val BUNDLER_ENVIRONMENT = "bundlerEnvironment"

class BundlerEnvironmentPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        extensions.create<BundlerEnvironmentExtension>(BUNDLER_ENVIRONMENT)
    }
}

internal val Project.bundlerEnvironment: BundlerEnvironmentExtension
    get() = extensions.getByName<BundlerEnvironmentExtension>(BUNDLER_ENVIRONMENT)
