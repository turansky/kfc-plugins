package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction

@CacheableTask
internal open class RelatedModuleProjects : DefaultTask() {
    @TaskAction
    fun calculate(): Set<Project> =
        project.relatedModuleProjects()
}

private fun Project.relatedModuleProjects(): Set<Project> =
    configurations
        .getByName(JS_MAIN_MODULE)
        .allDependencies
        .asSequence()
        .filterIsInstance<ProjectDependency>()
        .map { it.dependencyProject }
        .toSet()
