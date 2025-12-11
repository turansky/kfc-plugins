import * as process from 'node:process'
import {defineConfig, loadEnv} from 'vite'
import react from '@vitejs/plugin-react'
import reactRefreshKotlinJs from '@porotkin/vite-plugin-react-kotlinjs'

const getSubdir = (name) => name?.match(/\.woff2?/) ? 'fonts/' : ''
const entryFileNames = 'static/[name].[hash].js'
const chunkFileNames = entryFileNames
const assetFileNames = ({name}) => `static/${getSubdir(name)}[name].[hash].[ext]`

export default defineConfig(({mode}) => {
    const env = loadEnv(mode, process.cwd(), '')
    return {
        plugins: [
            reactRefreshKotlinJs(),
            react({include: /\.(mjs|js)$/}),
        ],
        root: 'kotlin',
        build: {
            rollupOptions: {
                input: {
                    'vite-dev-app': env.ENTRY_PATH,
                },
                output: {
                    entryFileNames: entryFileNames,
                    chunkFileNames: chunkFileNames,
                    assetFileNames: assetFileNames,
                },
            },
        },
        server: {
            proxy: {
                '^(?!.+\.m?js[\?*]?)(?!/@vite/client)(?!/@react-refresh)': {
                    target: 'http://localhost:8081',
                    changeOrigin: true,
                    secure: false,
                    configure: (proxy, _options) => {
                        proxy.on('error', (err, _req, _res) => {
                            console.log('proxy error', err);
                        });
                        proxy.on('proxyReq', (proxyReq, req, _res) => {
                            console.log('Sending Request to the Target:', req.method, req.url);
                        });
                        proxy.on('proxyRes', (proxyRes, req, _res) => {
                            console.log('Received Response from the Target:', proxyRes.statusCode, req.url);
                        });
                    },
                },
            },
        },
    }
})
