package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import java.io.File

private val RESOURCES = listOf(
    "src/commonMain/resources",
    "src/clientCommonMain/resources",
    "src/jsMain/resources",

    "src/main/resources"
)

internal fun Project.relatedResources(): List<File> =
    RESOURCES.asSequence()
        .map(projectDir::resolve)
        .filter { it.exists() }
        .filter { it.isDirectory }
        .toList()
