package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import java.io.File

private val RESOURCES = listOf(
    "src/commonMain/resources",
    "src/clientCommonMain/resources",
    "src/jsMain/resources",

    "src/main/resources"
)

internal fun Project.relatedResources(
    cache: RelatedProjectsCache,
): List<File> =
    relatedProjects(cache)
        .asSequence()
        .map { it.projectDir }
        .flatMap { RESOURCES.asSequence().map(it::resolve) }
        .filter { it.exists() }
        .filter { it.isDirectory }
        .toList()
