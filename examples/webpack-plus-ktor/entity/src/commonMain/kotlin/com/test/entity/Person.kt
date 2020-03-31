package com.test.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Serializable
data class Person(
    val firstName: String,
    val lastName: String
)

private val json = Json(JsonConfiguration.Stable)

fun List<Person>.toJsonString(): String =
    json.stringify(
        serializer = Person.serializer().list,
        value = this
    )

fun String.parsePersonList(): List<Person> =
    json.parse(
        deserializer = Person.serializer().list,
        string = this
    )
