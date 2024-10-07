import {defineConfig} from 'vite'

export default defineConfig({
    root: "kotlin",
    server: {
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                changeOrigin: true,
                secure: false,
            },
        },
    },
})
