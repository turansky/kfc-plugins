@file:Suppress("JSLastCommaInObjectLiteral")

package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Task
import java.io.File

internal const val ENTRY_PATH: String = "ENTRY_PATH"
internal const val USE_SOURCE_MAPS: String = "USE_SOURCE_MAPS"

// language=javascript
private val DEFAULT_VITE_CONFIG: String = """
import * as process from "node:process";
import {defineConfig, loadEnv} from 'vite'
import sourcemaps from 'rollup-plugin-sourcemaps'

export default defineConfig(({mode}) => {
    const env = loadEnv(mode, process.cwd(), '')
    return {
        plugins: !!env.$USE_SOURCE_MAPS ? [sourcemaps()] : undefined,
        build: {
            rollupOptions: {
                input: [
                    env.$ENTRY_PATH,
                ],
                output: {
                    entryFileNames: '[name].js',
                    sourcemapIgnoreList: (relativeSourcePath) => {
                        // will ignore-list all files with node_modules in their paths
                        return relativeSourcePath.includes('node_modules')
                    },
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
