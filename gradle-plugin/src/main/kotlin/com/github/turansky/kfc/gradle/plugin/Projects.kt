package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.internal.artifacts.dependencies.DefaultProjectDependency

private val IMPLEMENTATION = "implementation"

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
