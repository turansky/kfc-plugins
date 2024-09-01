@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.file.RegularFile

fun getViteConfig(
    project: Project,
    entryFile: RegularFile,
): String {
    val entryName = entryFile.asFile.nameWithoutExtension
    val entryPath = entryFile.asFile.absolutePath

    val configFileTemplate = project.layout.projectDirectory
        .file(Vite.configFile)
        .asFile

    if (configFileTemplate.exists()) {
        return configFileTemplate.readText()
            .replace("%ENTRY_PATH%", entryPath)
    }

    return getDefaultViteConfig(
        entryName = entryName,
        entryPath = entryPath,
    )
}

// language=javascript
private fun getDefaultViteConfig(
    entryName: String,
    entryPath: String,
): String = """
import { defineConfig } from 'vite'

export default defineConfig({
    build: {
        rollupOptions: {
            input: {
                '$entryName': '$entryPath',
            },
            output: {
                entryFileNames: '[name].js',
            },
        },
    },
})
""".trimIndent()
