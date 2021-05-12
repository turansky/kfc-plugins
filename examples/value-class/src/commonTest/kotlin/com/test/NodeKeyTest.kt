package com.test

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class NodeKeyTest {
    @Test
    fun string() {
        val id = NodeKeyS("1234")
        val data = Json.encodeToString(NodeKeyS.serializer(), id)
        val newId = Json.decodeFromString(NodeKeyS.serializer(), data)

        assertEquals(id, newId)
    }
}
