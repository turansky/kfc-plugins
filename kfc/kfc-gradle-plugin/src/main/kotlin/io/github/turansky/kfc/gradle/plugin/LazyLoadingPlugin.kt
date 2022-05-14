package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import java.io.File

private val REACT_COMPONENT_IMPORT = Regex("""__react_component_import__\((.+?)\)""")

class LazyLoadingPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.configureEach<KotlinJsCompile> {
            doLast {
                addLazySupport(File(kotlinOptions.outputFile!!))
            }
        }
    }
}

private fun addLazySupport(
    file: File,
) {
    if (file.extension != "js")
        return

    val appDir = file.parentFile
    val appName = file.name.substringBeforeLast(".")

    val content = file.readText()

    val (bodySource, librariesSource) = content.split("\n  main();\n  return _;\n}(module.exports, ")

    val body = bodySource.substringAfter("{\n")
        .replace(REACT_COMPONENT_IMPORT) {
            val componentName = it.groups[1]!!.value
            "import('./${appName}__$componentName')"
        }

    val libraryIds = bodySource
        .substringBefore("{\n")
        .removeSurrounding("(function (_, ", ") ")
        .split(", ")

    val imports = librariesSource
        .substringBefore("));")
        .splitToSequence(", ")
        .map { it.removeSurrounding("require(", ")") }
        .mapIndexed { index, library -> "import ${libraryIds[index]} from $library;" }
        .joinToString("\n")

    val componentNames = REACT_COMPONENT_IMPORT
        .findAll(content)
        .map { it.groups[1]!!.value }

    val exports = componentNames.asSequence()
        .plus("main")
        .joinToString("\n", "export {\n", "\n};") {
            "  $it,"
        }

    val mainLibName = "${appName}__lib"
    appDir.resolve("$mainLibName.js")
        .writeText("$imports\n\n$body\n\n$exports")

    for (componentName in componentNames) {
        appDir.resolve("${appName}__$componentName.js")
            .writeText(
                // language=JavaScript
                """
                import { $componentName } from './$mainLibName'
            
                export default $componentName
                """.trimIndent()
            )
    }

    file.writeText(
        // language=JavaScript
        """
        import { main } from './$mainLibName'
        
        main()
        """.trimIndent()
    )
}
