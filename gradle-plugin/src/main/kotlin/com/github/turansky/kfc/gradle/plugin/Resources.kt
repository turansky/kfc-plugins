package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import java.io.File

private val RESOURCES = "src/main/resources"

internal fun Project.relatedResources(): Set<File> {
    return relatedProjects()
        .asSequence()
        .map { it.projectDir }
        .map { it.resolve(RESOURCES) }
        .filter { it.exists() }
        .filter { it.isDirectory }
        .toSet()
}
