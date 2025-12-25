package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Task

internal fun Task.dependsOnNpmInstallTasks(
    jsPlatform: JsPlatform,
) {
    val nodeJsRoot = project.getNodeJsRootExtension(jsPlatform)

    dependsOn(nodeJsRoot.npmInstallTaskProvider)
    dependsOn(nodeJsRoot.packageManagerExtension.map { it.postInstallTasks })
}
