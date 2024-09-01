import {defineConfig} from 'vite'

export default defineConfig({
    build: {
        rollupOptions: {
            input: {
                'main': '%ENTRY_PATH%',
            },
            output: {
                entryFileNames: '[name].[hash].js',
            },
        },
    },
})
