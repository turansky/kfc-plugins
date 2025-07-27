@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Task
import java.io.File

internal const val ENTRY_PATH: String = "ENTRY_PATH"

// language=javascript
private val DEFAULT_VITE_CONFIG: String = """
import { cwd } from 'node:process'
import {defineConfig, loadEnv} from 'vite'
import sourcemaps from 'rollup-plugin-sourcemaps'

export default defineConfig(({mode}) => {
    const env = loadEnv(mode, cwd(), '')
    return {
        plugins: [sourcemaps()],
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
