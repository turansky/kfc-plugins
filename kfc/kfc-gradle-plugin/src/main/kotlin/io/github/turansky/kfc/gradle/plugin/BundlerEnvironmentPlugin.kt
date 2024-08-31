package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class BundlerEnvironmentPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        extensions.create(
            BundlerEnvironmentExtension::class.java,
            "bundlerEnvironment",
            BundlerEnvironmentExtensionImpl::class.java,
            project,
        )
    }
}
