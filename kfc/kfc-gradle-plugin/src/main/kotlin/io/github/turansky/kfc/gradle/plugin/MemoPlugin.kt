package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce

class MemoPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.configureEach<KotlinJsDce> {
            doLast {
                val kotlinReact = getOutputDirectory(this)
                    .resolve("kotlin-react.js")

                val content = kotlinReact.readText()
                val newContent = addMemoization(content)
                if (newContent != content) {
                    kotlinReact.writeText(newContent)
                }
            }
        }
    }
}

private fun addMemoization(
    content: String,
): String =
    content
        .replace(
            ".FC_4y0n3r$ = FC;",
            ".FC_4y0n3r$ = (block) => \$module\$react.memo(FC(block));",
        )
        .replace(
            ".VFC_3ulnvg$ = VFC;",
            ".VFC_3ulnvg$ = (block) => \$module\$react.memo(VFC(block));",
        )
        .replace(
            ".ForwardRef_kujimp$ = ForwardRef;",
            ".ForwardRef_kujimp$ = (block) => \$module\$react.memo(ForwardRef(block));",
        )
