package io.github.turansky.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/api/data") {
            call.respondText("Valuable data response!")
        }
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
