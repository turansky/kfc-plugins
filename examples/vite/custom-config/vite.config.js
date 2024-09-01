import {defineConfig} from 'vite'

export default defineConfig({
    build: {
        rollupOptions: {
            input: {
                'main': '%MAIN_PATH%',
            },
            output: {
                entryFileNames: '[name].[hash].js',
            },
        },
    },
})
