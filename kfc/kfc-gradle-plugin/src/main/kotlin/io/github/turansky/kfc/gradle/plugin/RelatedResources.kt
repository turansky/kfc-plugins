package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction
import java.io.File

private val RESOURCES = listOf(
    "src/commonMain/resources",
    "src/clientCommonMain/resources",
    "src/jsMain/resources",

    "src/main/resources"
)

@CacheableTask
internal open class RelatedResources : DefaultTask() {
    @TaskAction
    fun calculate(): List<File> =
        project.relatedResources()
}

fun Project.relatedResources(): List<File> =
    relatedProjects()
        .asSequence()
        .map { it.projectDir }
        .flatMap { RESOURCES.asSequence().map(it::resolve) }
        .filter { it.exists() }
        .filter { it.isDirectory }
        .toList()

private fun Project.relatedProjects(): Set<Project> {
    val configuration = configurations.findByName(JS_MAIN_IMPLEMENTATION)
        ?: return emptySet()

    return configuration
        .allDependencies
        .asSequence()
        .filterIsInstance<ProjectDependency>()
        .map { it.dependencyProject }
        .flatMap { sequenceOf(it) + it.relatedProjects() }
        .plus(this)
        .toSet()
}
