package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

internal open class GenerateDependencyJson : DefaultTask() {
    @TaskAction
    private fun generate() {
        val npmDependencies = project.nmpDependencies()
            .takeIf { it.isNotEmpty() }
            ?: return

        val devDependencies = npmDependencies
            .joinToString(",\n")
            { """        "${it.name}": "${it.version}"""" }

        temporaryDir.resolve("package.json").writeText(
            // language=JSON
            """
            |{
            |    "name": "${project.npmName}",
            |    "devDependencies": {
            |$devDependencies
            |    }
            |}
            """.trimMargin()
        )
    }
}

private val Project.npmName: String
    get() = if (this != rootProject) {
        "${rootProject.name}/$name"
    } else {
        name
    }
