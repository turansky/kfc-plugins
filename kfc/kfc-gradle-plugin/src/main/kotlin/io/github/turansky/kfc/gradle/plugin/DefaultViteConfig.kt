@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

internal const val ENTRY_PATH: String = "ENTRY_PATH"

// language=javascript
internal val DEFAULT_VITE_CONFIG: String = """
import {cwd} from 'node:process'
import {defineConfig, loadEnv} from 'vite'

export default defineConfig(({mode}) => {
    const env = loadEnv(mode, cwd(), '')
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
