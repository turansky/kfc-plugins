package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import java.io.File

open class GenerateAssets : DefaultTask() {
    init {
        group = DEFAULT_TASK_GROUP
    }

    @get:Input
    var pkg: String? = null

    @Optional
    @get:Input
    var templateColor: String? = null

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

        val symbolConstants = mutableListOf<String>()

        for (file in svgFiles) {
            val fullPath = file.toRelativeString(resourcesDirectory)
            val path = fullPath.substringBeforeLast(".")
            val name = path
                .replace("/", "__")
                .replace("-", "_")
                .uppercase() + "_CONTENT"

            val symbolId = "kfc-gis__" + path.replace("/", "__")
            val content = SVG.symbol(
                id = symbolId,
                source = file.readText(),
                templateColor = templateColor,
            )
            val declaration = "internal const val $name = \"\"\"$content\"\"\""

            createFile(
                path = "${path}.kt",
                content = declaration,
            )

            symbolConstants += name
        }

        createFile(
            path = "AssetRegistry.kt",
            content = assetRegistryContent(symbolConstants),
        )
    }
}

// language=kotlin
private val ASSET_REGISTRY_TEMPLATE = """
object AssetRegistry {

const val SYMBOLS_CONTENT: String = ${"\"\"\""}
__SYMBOLS__
${"\"\"\""}
    
    fun getSymbolId(path: String): String = 
        "kfc-gis__" + path.replace("/", "__")
}
"""

private fun assetRegistryContent(
    symbolNames: List<String>,
): String {
    val symbolsContent = symbolNames.joinToString("\n") { name ->
        "${'$'}$name"
    }

    return ASSET_REGISTRY_TEMPLATE.replace("__SYMBOLS__", symbolsContent)
}
