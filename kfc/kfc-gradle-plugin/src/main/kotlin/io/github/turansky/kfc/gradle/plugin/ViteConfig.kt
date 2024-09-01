@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

internal const val ENTRY_PATH: String = "ENTRY_PATH"

// language=javascript
private val DEFAULT_VITE_CONFIG: String = """
import * as process from "node:process";
import {defineConfig, loadEnv} from 'vite'

export default defineConfig(({mode}) => {
    const env = loadEnv(mode, process.cwd(), '')
    return {
        build: {
            rollupOptions: {
                input: [
                    env.$ENTRY_PATH,
                ],
                output: {
                    entryFileNames: '[name].js',
                },
            },
        },
    }
})
""".trimIndent()

fun getViteConfig(
    project: Project,
): String {
    val configFileTemplate = project.layout.projectDirectory
        .file(Vite.configFile)
        .asFile

    return if (configFileTemplate.exists()) {
        configFileTemplate.readText()
    } else DEFAULT_VITE_CONFIG
}
