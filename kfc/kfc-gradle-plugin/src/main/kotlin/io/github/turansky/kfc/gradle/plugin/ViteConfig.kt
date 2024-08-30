@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

// language=javascript
fun getViteConfig(
    project: Project,
): String = """
export default defineConfig({
    build: {
        root: 'kotlin',
        outDir: 'my-special-dist',
        rollupOptions: {
            input: {
                'main': resolve(__dirname, '${project.jsModuleName}.mjs'),
            },
            output: {
                entryFileNames: '${project.jsOutputFileName}',
            },
        },
    },
})
""".trimIndent()
