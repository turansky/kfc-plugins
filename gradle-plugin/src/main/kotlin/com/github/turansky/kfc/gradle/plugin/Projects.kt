package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.internal.artifacts.dependencies.DefaultProjectDependency

// TODO: optimize calculation
internal fun Project.relatedProjects(): Set<Project> {
    return configurations.getByName("implementation")
        .allDependencies
        .asSequence()
        .filterIsInstance<DefaultProjectDependency>()
        .map { it.dependencyProject }
        .flatMap { sequenceOf(it) + it.relatedProjects() }
        .plus(this)
        .toSet()
}
