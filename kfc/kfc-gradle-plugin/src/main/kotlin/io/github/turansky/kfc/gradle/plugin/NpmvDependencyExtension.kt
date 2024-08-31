package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmDependency
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmDependencyExtension
import javax.inject.Inject

abstract class NpmvDependencyExtension
@Inject
constructor(
    private val project: Project,
) {
    operator fun invoke(name: String): NpmDependency {
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
