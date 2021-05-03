package com.test

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class NodeKeyTest {
    @Test
    fun serialization() {
        val id = NodeKey("1234")
        val data = Json.encodeToString(NodeKey.serializer(), id)
        val newId = Json.decodeFromString(NodeKey.serializer(), data)

        assertEquals(id, newId)
    }
}
