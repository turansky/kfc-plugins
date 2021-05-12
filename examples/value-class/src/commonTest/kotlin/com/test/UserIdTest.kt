package com.test

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class UserIdTest {
    @Test
    fun string_encode() {
        val id = UserIdS("user-1234")
        val data = Json.encodeToString(UserIdS.serializer(), id)
        assertEquals(data, """"user-1234"""")
    }

    @Test
    fun string_decode() {
        val id = UserIdS("user-1234")
        val newId = Json.decodeFromString(UserIdS.serializer(), """"user-1234"""")
        assertEquals(id, newId)
    }

    @Test
    fun int_encode() {
        val id = UserIdI(1234)
        val data = Json.encodeToString(UserIdI.serializer(), id)
        assertEquals(data, "1234")
    }

    @Test
    fun int_decode() {
        val id = UserIdI(1234)
        val newId = Json.decodeFromString(UserIdI.serializer(), "1234")
        assertEquals(id, newId)
    }

    @Test
    fun double_encode() {
        val id = UserIdD(1234.56)
        val data = Json.encodeToString(UserIdD.serializer(), id)
        assertEquals(data, "1234.56")
    }

    @Test
    fun double_decode() {
        val id = UserIdD(1234.56)
        val newId = Json.decodeFromString(UserIdD.serializer(), "1234.56")
        assertEquals(id, newId)
    }
}
