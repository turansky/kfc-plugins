package com.test

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class UserIdTest {
    @Test
    fun string() {
        val id = UserIdS("1234")
        val data = Json.encodeToString(UserIdS.serializer(), id)
        val newId = Json.decodeFromString(UserIdS.serializer(), data)

        assertEquals(id, newId)
    }

    @Test
    fun int() {
        val id = UserIdI(1234)
        val data = Json.encodeToString(UserIdI.serializer(), id)
        val newId = Json.decodeFromString(UserIdI.serializer(), data)

        assertEquals(id, newId)
    }

    @Test
    fun double() {
        val id = UserIdD(1234.0)
        val data = Json.encodeToString(UserIdD.serializer(), id)
        val newId = Json.decodeFromString(UserIdD.serializer(), data)

        assertEquals(id, newId)
    }
}
