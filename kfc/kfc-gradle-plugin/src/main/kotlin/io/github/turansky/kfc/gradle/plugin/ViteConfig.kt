@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Task
import java.io.File

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

fun Task.defaultViteConfig(): File {
    val file = temporaryDir.resolve(Vite.configFile)
    file.writeText(DEFAULT_VITE_CONFIG)
    return file
}
