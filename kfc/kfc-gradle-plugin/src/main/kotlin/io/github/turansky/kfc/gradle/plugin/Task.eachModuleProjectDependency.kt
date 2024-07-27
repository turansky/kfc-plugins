package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.Task

internal fun Task.eachModuleProjectDependency(
    action: (Project) -> Unit,
) {
    project.relatedModuleProjects()
        .forEach(action)
}
