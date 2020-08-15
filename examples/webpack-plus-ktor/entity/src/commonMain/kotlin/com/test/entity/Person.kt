package com.test.entity

data class Person(
    val firstName: String,
    val lastName: String
)

fun List<Person>.toJsonString(): String =
    joinToString()

fun String.parsePersonList(): List<Person> =
    TODO()
