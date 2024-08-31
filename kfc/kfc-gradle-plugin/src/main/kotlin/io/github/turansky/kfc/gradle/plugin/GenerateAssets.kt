package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import java.io.File

abstract class GenerateAssets : DefaultTask() {
    init {
        group = DEFAULT_TASK_GROUP
    }

    @get:Input
    var multiplatformMode: Boolean = false

    @get:Input
    var pkg: String? = null

    @get:Input
    var factoryName: String? = null

    @Optional
    @get:Input
    var templateColor: String? = null

    @get:InputDirectory
    var resourcesDirectory: File? = null

    @Optional
    @get:InputFile
    var mobileListFile: File? = null

    @get:OutputDirectory
    val clientCommonOutputDirectory: File
        get() = temporaryDir.resolve("src/clientCommon")

    @get:OutputDirectory
    val mobileCommonOutputDirectory: File
        get() = temporaryDir.resolve("src/mobileCommon")

    @get:OutputDirectory
    val jsOutputDirectory: File
        get() = temporaryDir.resolve("src/js")

    private fun getCommonPredicate(): (path: String) -> Boolean {
        if (!multiplatformMode)
            return { false }

        val listFile = mobileListFile
            ?: return { false }

        val items = listFile.readText()
            .splitToSequence("\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .toSet()

        if (items.isEmpty())
            return { false }

        return { it in items }
    }

    @TaskAction
    private fun generateAssets() {
        jsOutputDirectory.deleteRecursively()

        val assetsPackage = requireNotNull(pkg)
        val assetFactory = requireNotNull(factoryName)
        val resourcesDirectory = requireNotNull(resourcesDirectory)

        val svgFiles = resourcesDirectory.walkTopDown()
            .filter { it.isFile }
            .filter { it.extension == "svg" }
            .toList()

        fun createFile(
            path: String,
            content: String,
            parentDirectory: File,
        ) {
            val file = parentDirectory.resolve(path)
            file.parentFile.mkdirs()

            val text = buildString {
                append("$AUTO_GENERATION_COMMENT\n")
                append("package $assetsPackage \n\n")
                append(content)
            }
            file.writeText(text)
        }

        val isCommon = getCommonPredicate()

        val icons = mutableListOf<Icon>()
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
                parentDirectory = jsOutputDirectory,
            )

            icons += Icon(path, common = isCommon(path))
            symbolConstants += name
        }

        if (multiplatformMode) {
            createFile(
                path = "Icons.kt",
                content = commonIconsContent(assetFactory, icons),
                parentDirectory = clientCommonOutputDirectory,
            )

            createFile(
                path = "Icons.kt",
                content = mobileIconsContent(assetFactory, icons),
                parentDirectory = mobileCommonOutputDirectory,
            )
        }

        createFile(
            path = "Icons.kt",
            content = jsIconsContent(assetFactory, icons, multiplatformMode),
            parentDirectory = jsOutputDirectory,
        )

        createFile(
            path = "AssetRegistry.kt",
            content = assetRegistryContent(symbolConstants),
            parentDirectory = jsOutputDirectory,
        )
    }
}

private class Icon(
    val path: String,
    val common: Boolean,
) {
    val name: String = path.splitToSequence("/")
        .map { part ->
            part.splitToSequence("-").joinToString("") {
                when (it) {
                    "2d", "3d",
                    -> it.uppercase()

                    // TODO: use custom `capitalized`
                    else -> it.replaceFirstChar { char ->
                        char.uppercase()
                    }
                }
            }
        }.joinToString("_")
}

private fun commonIconsContent(
    factoryName: String,
    icons: List<Icon>,
): String {
    val content = icons
        .filter { it.common }
        .joinToString("\n") { icon ->
            "    val ${icon.name}: $factoryName"
        }

    return "expect object Icons {\n" +
            content +
            "\n}\n"
}

private fun jsIconsContent(
    factoryName: String,
    icons: List<Icon>,
    actualMode: Boolean,
): String {
    val content = icons.joinToString("\n") { icon ->
        val symbolId = "kfc-gis__" + icon.path.replace("/", "__")

        val modifier = if (actualMode && icon.common) "actual" else ""
        "    $modifier val ${icon.name}: $factoryName = $factoryName(\"$symbolId\")"
    }

    val modifier = if (actualMode) "actual" else ""
    return "$modifier object Icons {\n" +
            content +
            "\n}\n"
}

private fun mobileIconsContent(
    factoryName: String,
    icons: List<Icon>,
): String {
    val content = icons
        .filter { it.common }
        .joinToString("\n") { icon ->
            val parameters = icon.path.splitToSequence("/")
                .map { "\"$it\"" }
                .joinToString(", ")

            "    actual val ${icon.name}: $factoryName = $factoryName($parameters)"
        }

    return "actual object Icons {\n" +
            content +
            "\n}\n"
}

// language=kotlin
private val ASSET_REGISTRY_TEMPLATE = """
internal object AssetRegistry {

const val SYMBOLS_CONTENT: String = ${"\"\"\""}
__SYMBOLS__
${"\"\"\""}
    
    fun getSymbolId(path: String): String = 
        "kfc-gis__" + path.replace("/", "__")
}
"""

// language=kotlin
private val AUTO_GENERATION_COMMENT = """
// Automatically generated by KFC plugins(https://github.com/turansky/kfc-plugins) - DO NOT EDIT -
// Add assets in the `sourceSet/assets` folder
""".trimIndent()

private fun assetRegistryContent(
    symbolNames: List<String>,
): String {
    val symbolsContent = symbolNames.joinToString("\n") { name ->
        "${'$'}$name"
    }

    return ASSET_REGISTRY_TEMPLATE.replace("__SYMBOLS__", symbolsContent)
}
