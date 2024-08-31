import {resolve} from 'node:path'
import {defineConfig} from 'vite'

export default defineConfig({
    mode: '%MODE%',
    build: {
        outDir: '%OUT_DIR%',
        emptyOutDir: true,
        rollupOptions: {
            input: {
                'main': resolve(__dirname, '%MAIN%'),
            },
            output: {
                entryFileNames: '[name]-[hash].js',
                sourcemap: '%SOURCE_MAP%',
            },
        },
    },
})
