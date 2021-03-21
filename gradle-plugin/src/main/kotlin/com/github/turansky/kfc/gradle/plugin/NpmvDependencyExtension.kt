package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmDependency
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmDependencyExtension

interface NpmvDependencyExtension {
    operator fun invoke(name: String): NpmDependency
}

internal open class NpmvDependencyExtensionImpl(
    private val project: Project
) : NpmvDependencyExtension {
    override fun invoke(name: String): NpmDependency {
        val npm = project.dependencies.the<NpmDependencyExtension>()
        return npm(name, project.version(name))
    }
}

private fun Project.version(target: String): String {
    val id = target
        .removePrefix("@")
        .replace("/", "-")

    return property("${id}.version") as String
}
