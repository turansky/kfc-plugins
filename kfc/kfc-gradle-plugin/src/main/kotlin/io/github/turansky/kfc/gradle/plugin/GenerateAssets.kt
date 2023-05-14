package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

open class GenerateAssets : DefaultTask() {
    init {
        group = DEFAULT_TASK_GROUP
    }

    @get:Input
    var pkg: String? = null

    @get:InputDirectory
    var resourcesDirectory: File? = null

    @get:OutputDirectory
    val outputDirectory: File
        get() = temporaryDir

    @TaskAction
    private fun generateAssets() {
        val assetsPackage = requireNotNull(pkg)
        val resourcesDirectory = requireNotNull(resourcesDirectory)

        val svgFiles = resourcesDirectory.walkTopDown()
            .filter { it.isFile }
            .filter { it.extension == "svg" }
            .toList()

        fun createFile(
            path: String,
            content: String,
        ) {
            val file = resourcesDirectory.resolve(path)
            file.writeText("package $assetsPackage\n\n$content")
        }

        for (file in svgFiles) {
            val path = file.toRelativeString(resourcesDirectory)
            val name = path.substringBeforeLast(".")
                .replace("/", "__")
                .replace("-", "_")
                .uppercase()

            val content = XML.compressedContent(file.readText())
            val declaration = "internal val $name = \"\"\"$content\"\"\""

            createFile(
                path = "${path}.kt",
                content = declaration,
            )
        }
    }
}
