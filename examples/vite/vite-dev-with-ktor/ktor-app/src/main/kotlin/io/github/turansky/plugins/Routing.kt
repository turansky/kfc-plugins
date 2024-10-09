package io.github.turansky.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.body
import kotlinx.html.lang
import kotlinx.html.script
import kotlinx.html.title
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
                }

                body {
                    script {
                        src = "./vite-dev-app.mjs"
                        async = true
                        type = "module"
                    }
                }
            }
        }
    }
}
