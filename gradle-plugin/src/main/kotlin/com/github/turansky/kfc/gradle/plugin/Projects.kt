package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.internal.artifacts.dependencies.DefaultProjectDependency
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmDependency

private val IMPLEMENTATION = "implementation"

internal val Project.jsProjectId: String
    get() = "${rootProject.name}-$name"

// TODO: optimize calculation
internal fun Project.relatedProjects(): Set<Project> {
    val configuration = configurations.findByName(IMPLEMENTATION)
        ?: return emptySet()

    return configuration
        .allDependencies
        .asSequence()
        .filterIsInstance<DefaultProjectDependency>()
        .map { it.dependencyProject }
        .flatMap { sequenceOf(it) + it.relatedProjects() }
        .plus(this)
        .toSet()
}

internal fun Project.nmpDependencies(): List<NpmDependency> {
    val configuration = configurations.findByName(IMPLEMENTATION)
        ?: return emptyList()

    return configuration
        .allDependencies
        .filterIsInstance<NpmDependency>()
}
