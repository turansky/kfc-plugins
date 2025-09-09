package io.github.turansky.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import java.io.File

fun Application.configureRouting() {
    val currentDir = File("").absoluteFile
    val mode = "production"
    val staticDir = currentDir.resolve("../vite-dev-app/build/dist/$mode/static")
    val jsModule = staticDir.listFiles()?.single()

    routing {
        staticFiles("/static", staticDir)
        get("/api/data") {
            call.respondText("current dir is: ${currentDir.absolutePath}\nstatic dir is: ${staticDir.absolutePath}\njsFile: ${jsModule?.name}")
        }
        get("/") {
            call.respondHtml {
                lang = "en"

                head {
                    title = "Ktor"

                    reactRefreshPreamble()
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
    }
}

private fun HEAD.reactRefreshPreamble() {
    script {
        type = "module"

        unsafe {
            // language=JavaScript
            +"""
                import { injectIntoGlobalHook } from "/@react-refresh";
                injectIntoGlobalHook(window);
                window.${'$'}RefreshReg$ = () => {};
                window.${'$'}RefreshSig$ = () => (type) => type;
            """.trimIndent()
        }
    }
}

private fun HEAD.viteClient() {
    script {
        src = "/@vite/client"
        type = "module"
    }
}
