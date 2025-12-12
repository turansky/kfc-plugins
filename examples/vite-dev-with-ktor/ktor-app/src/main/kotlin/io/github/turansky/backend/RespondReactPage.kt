package io.github.turansky.backend

import io.ktor.server.application.*
import io.ktor.server.html.*
import kotlinx.html.*

internal suspend fun ApplicationCall.respondReactPage() {
    respondHtml {
        lang = "en"

        head {
            title = "Ktor"

            viteClient()

            style {
                unsafe {
                    +"""
                        html, body {
                            height: 100%;
                            width: 100%;
                            margin: 0;
                        }
                    """.trimIndent()
                }
            }
        }

        body {
            script {
                src = "./vite-dev-app.mjs"
                type = "module"
            }
        }
    }
}

private fun HEAD.viteClient() {
    script {
        src = "/@vite/client"
        type = "module"
    }
}
