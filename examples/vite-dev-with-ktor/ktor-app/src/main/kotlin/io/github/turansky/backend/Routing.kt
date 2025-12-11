package io.github.turansky.backend.plugins

import io.github.turansky.backend.respondReactPage
import io.github.turansky.entity.ServerData
import io.github.turansky.entity.util.encodeToString
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    val currentDir = File("").absoluteFile
    val mode = "production"
    val staticDir = currentDir.resolve("../vite-dev-app/build/dist/js/$mode/static")
    val jsModule = staticDir.listFiles()?.single()

    routing {
        staticFiles("/static", staticDir)
        get("/api/data") {
            call.respondText(
                encodeToString(
                    ServerData.serializer(),
                    ServerData(
                        serverDir = currentDir.absolutePath,
                        staticDir = staticDir.absolutePath,
                        entryFile = jsModule!!.absolutePath,
                    ),
                ),
                ContentType.Application.Json,
            )
        }
        get("/") {
            call.respondReactPage()
        }
    }
}
