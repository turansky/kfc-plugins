package com.test

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class UserIdTest {
    @Test
    fun serialization() {
        val id = UserId("1234")
        val data = Json.encodeToString(UserId.serializer(), id)
        val newId = Json.decodeFromString(UserId.serializer(), data)

        assertEquals(id, newId)
    }
}
