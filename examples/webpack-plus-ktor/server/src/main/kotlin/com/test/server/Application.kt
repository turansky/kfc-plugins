package com.test.server

import com.test.entity.Person
import com.test.entity.toJsonString
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing

private val APPLICATION_JSON = ContentType.parse("application/json")

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
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
