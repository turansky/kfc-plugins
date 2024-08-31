@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.file.Directory

fun getViteConfig(
    project: Project,
    mode: ViteMode,
    outputDirectory: Directory,
): String {
    val outDir = outputDirectory.asFile.absolutePath
    val mainPath = "kotlin/${project.jsModuleName}.mjs"
    val sourceMaps = project.property(SOURCE_MAPS)

    val configFileTemplate = project.layout.projectDirectory
        .file(Vite.configFile)
        .asFile

    if (configFileTemplate.exists()) {
        return configFileTemplate.readText()
            .replace("%MODE%", mode.toString())
            .replace("%OUT_DIR%", outDir)
            .replace("%MAIN%", mainPath)
            .replace("'%SOURCE_MAP%'", sourceMaps.toString())
    }

    return getDefaultViteConfig(
        project = project,
        mode = mode,
        outDir = outDir,
        mainPath = mainPath,
        sourceMaps = sourceMaps,
    )
}

// language=javascript
private fun getDefaultViteConfig(
    project: Project,
    mode: ViteMode,
    outDir: String,
    mainPath: String,
    sourceMaps: Boolean,
): String = """
import { resolve } from 'node:path'
import { defineConfig } from 'vite'

export default defineConfig({
    mode: '$mode',
    build: {
        outDir: '${outDir}',
        emptyOutDir: true,
        rollupOptions: {
            input: {
                'main': resolve(__dirname, '$mainPath'),
            },
            output: {
                entryFileNames: '${project.jsOutputFileName}',
                sourcemap: ${sourceMaps},
            },
        },
    },
})
""".trimIndent()
