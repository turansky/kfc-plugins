package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import java.io.File

private val DECLARATIONS = listOf(
    Regex("""\n  var (\S+) = \${'$'}module\${'$'}kotlin_react\.react\.FC_4y0n3r\$;"""),
    Regex("""\n  var (\S+) = \${'$'}module\${'$'}kotlin_react\.react\.VFC_3ulnvg\$;"""),
    Regex("""\n  var (\S+) = \${'$'}module\${'$'}kotlin_react\.react\.ForwardRef_kujimp\$;"""),
    Regex("""\n  var (\S+) = \${'$'}module\${'$'}react\.createContext;"""),
)

class ReactPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.configureEach<KotlinJsDce> {
            if ("Dev" !in name)
                return@configureEach

            doLast {
                val outputDirectory = getOutputDirectory(this)
                fileTree(outputDirectory).visit {
                    if (file.isCandidate()) {
                        val content = file.readText()
                        val newContent = addDisplayName(content)
                        if (newContent != content) {
                            file.writeText(newContent)
                        }
                    }
                }
            }
        }
    }
}

private fun File.isCandidate(): Boolean =
    name.endsWith(".js")
            && !name.endsWith(".meta.js")

private fun addDisplayName(
    content: String,
): String =
    DECLARATIONS.fold(content) { result, declaration ->
        val matchResult = declaration.find(result)
            ?: return@fold result

        val name = matchResult.groupValues[1]
        val usage = Regex("""\n  (\S+) = $name\(\S*\)\;""")
        addDisplayName(result, usage)
    }

private fun addDisplayName(
    content: String,
    usageRegex: Regex,
): String =
    content.replace(usageRegex) { result ->
        val varName = result.groupValues[1]
        val componentName = varName.substringBefore("_")
        "${result.value}  $varName.displayName = '$componentName';"
    }
