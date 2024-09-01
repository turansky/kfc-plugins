import {defineConfig, loadEnv} from 'vite'

export default defineConfig(({mode}) => {
    const env = loadEnv(mode, process.cwd(), '')
    return {
        build: {
            rollupOptions: {
                input: {
                    'main': env.ENTRY_PATH
                },
                output: {
                    entryFileNames: '[name].[hash].js',
                },
            },
        },
    }
})
