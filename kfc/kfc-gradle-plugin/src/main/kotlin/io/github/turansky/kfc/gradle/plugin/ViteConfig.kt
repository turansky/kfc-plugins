@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.file.Directory

// language=javascript
fun getViteConfig(
    project: Project,
    outputDirectory: Directory,
): String = """
import { resolve } from 'node:path'
import { defineConfig } from 'vite'

export default defineConfig({
    build: {
        root: 'kotlin',
        outDir: '${outputDirectory.asFile.absolutePath}',
        emptyOutDir: true,
        rollupOptions: {
            input: {
                'main': resolve(__dirname, 'kotlin/${project.jsModuleName}.mjs'),
            },
            output: {
                entryFileNames: '${project.jsOutputFileName}',
                sourcemap: ${project.property(SOURCE_MAPS)},
            },
        },
    },
})
""".trimIndent()
