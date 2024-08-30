// vite.config.js
import {resolve} from 'node:path'
import {defineConfig} from 'vite'

export default defineConfig({
    build: {
        outDir: '../my-special-dist',
        rollupOptions: {
            input: {
                'main': resolve(__dirname, 'vite-task-check.mjs'),
            },
            output: {
                entryFileNames: 'app.js',
            },
        },
    },
})
