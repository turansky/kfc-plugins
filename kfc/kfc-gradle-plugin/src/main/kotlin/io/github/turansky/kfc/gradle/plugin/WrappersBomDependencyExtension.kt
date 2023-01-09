package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

interface WrappersBomDependencyExtension {
    operator fun invoke(name: String): String
}

internal open class WrappersBomDependencyExtensionImpl(
    private val project: Project,
) : WrappersBomDependencyExtension {
    override fun invoke(name: String): String {
        val version = project.version("kotlin-wrappers")
        return "org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:$version"
    }
}

private fun Project.version(
    target: String,
): String =
    property("${target}.version") as String
