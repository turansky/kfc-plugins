package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

private class AssetEntry(
    val path: String,
    val name: String,
)

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
        get() = temporaryDir.resolve("src")

    @TaskAction
    private fun generateAssets() {
        outputDirectory.deleteRecursively()

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
            val file = outputDirectory.resolve(path)
            file.parentFile.mkdirs()
            file.writeText("package $assetsPackage\n\n$content")
        }

        val entries = mutableListOf<AssetEntry>()

        for (file in svgFiles) {
            val fullPath = file.toRelativeString(resourcesDirectory)
            val path = fullPath.substringBeforeLast(".")
            val name = path
                .replace("/", "__")
                .replace("-", "_")
                .uppercase() + "_CONTENT"

            val content = XML.compressedContent(file.readText())
            val declaration = "internal val $name = \"\"\"$content\"\"\""

            createFile(
                path = "${path}.kt",
                content = declaration,
            )

            entries.add(
                AssetEntry(
                    path = path,
                    name = name,
                )
            )
        }

        createFile(
            path = "AssetRegistry.kt",
            content = assetRegistryContent(entries),
        )
    }
}

// language=kotlin
private val ASSET_REGISTRY_TEMPLATE = """
object AssetRegistry {
    private val map: Map<String, String> = mapOf(
__ENTRIES__
    )
    
    operator fun get(path: String): String = 
        map.getValue(path)
}
""".trim()

private fun assetRegistryContent(
    entries: List<AssetEntry>,
): String {
    val mapEntries = entries.joinToString("\n") { entry ->
        """        "${entry.path}" to ${entry.name},"""
    }

    return ASSET_REGISTRY_TEMPLATE.replace("__ENTRIES__", mapEntries)
}
