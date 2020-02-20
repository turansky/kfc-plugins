package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

internal open class GenerateDependencyJson : DefaultTask() {
    @get:OutputFile
    val packageJson: File
        get() = temporaryDir.resolve("package.json")

    @TaskAction
    private fun generate() {
        val npmDependencies = project.nmpDependencies()
            .takeIf { it.isNotEmpty() }
            ?: return

        val devDependencies = npmDependencies
            .joinToString(",\n")
            { """        "${it.name}": "${it.version}"""" }

        packageJson.writeText(
            // language=JSON
            """
            |{
            |    "name": "${project.npmName}",
            |    "version": "0.0.0",
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
        "@${rootProject.name}/$name"
    } else {
        name
    }
