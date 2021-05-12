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

    @Test
    fun int() {
        val id = NodeKeyI(1234)
        val data = Json.encodeToString(NodeKeyI.serializer(), id)
        val newId = Json.decodeFromString(NodeKeyI.serializer(), data)

        assertEquals(id, newId)
    }

    @Test
    fun double() {
        val id = NodeKeyD(1234.0)
        val data = Json.encodeToString(NodeKeyD.serializer(), id)
        val newId = Json.decodeFromString(NodeKeyD.serializer(), data)

        assertEquals(id, newId)
    }
}
