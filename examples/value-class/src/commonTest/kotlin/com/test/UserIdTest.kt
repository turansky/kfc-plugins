package com.test

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class UserIdTest {
    @Test
    fun serialization() {
        val id = UserIdS("1234")
        val data = Json.encodeToString(UserIdS.serializer(), id)
        val newId = Json.decodeFromString(UserIdS.serializer(), data)

        assertEquals(id, newId)
    }
}
