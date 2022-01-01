package com.test.server

import com.test.entity.Person
import com.test.entity.toJsonString
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

private val APPLICATION_JSON = ContentType.parse("application/json")

fun Application.main() {
    routing {
        get("/persons") {
            call.respondText(
                contentType = APPLICATION_JSON,
                status = HttpStatusCode.OK
            ) { getPersons().toJsonString() }
        }
    }
}

private fun getPersons(): List<Person> =
    listOf(
        Person("Ivan", "Ivanov"),
        Person("Petr", "Petrov")
    )
