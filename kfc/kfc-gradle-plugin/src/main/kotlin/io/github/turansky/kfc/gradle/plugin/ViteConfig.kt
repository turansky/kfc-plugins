@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.file.RegularFile

fun getViteConfig(
    project: Project,
    entryFile: RegularFile,
): String {
    val entryPath = entryFile.asFile.absolutePath

    val configFileTemplate = project.layout.projectDirectory
        .file(Vite.configFile)
        .asFile

    if (configFileTemplate.exists()) {
        return configFileTemplate.readText()
            .replace("%ENTRY_PATH%", entryPath)
    }

    return getDefaultViteConfig(
        entryPath = entryPath,
    )
}

// language=javascript
private fun getDefaultViteConfig(
    entryPath: String,
): String = """
import { defineConfig } from 'vite'

export default defineConfig({
    build: {
        rollupOptions: {
            input: [
                '$entryPath',
            ],
            output: {
                entryFileNames: '[name].js',
            },
        },
    },
})
""".trimIndent()
