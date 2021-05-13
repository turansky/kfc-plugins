package com.test

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class UserTest {
    @Test
    fun serialization() {
        val user = User(
            UserIdS("user-1234"),
            UserIdI(1234),
            UserIdD(1234.56),
        )
        val data = Json.encodeToString(User.serializer(), user)
        val newUser = Json.decodeFromString(User.serializer(), data)

        assertEquals(user, newUser)
    }
}
