package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import java.io.File

private val RESOURCES = listOf(
    "src/jsMain/resources",
)

internal fun Project.relatedResources(): List<File> =
    RESOURCES.asSequence()
        .map { file(it) }
        .filter { it.isDirectory }
        .toList()
