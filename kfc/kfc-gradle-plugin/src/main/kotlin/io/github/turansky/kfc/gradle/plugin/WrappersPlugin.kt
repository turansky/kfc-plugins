package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class WrappersPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        extensions.create(
            WrappersBomDependencyExtension::class.java,
            "wrappersBom",
            WrappersBomDependencyExtensionImpl::class.java,
            project
        )

        extensions.create(
            WrappersDependencyExtension::class.java,
            "wrappers",
            WrappersDependencyExtensionImpl::class.java,
        )
    }
}
