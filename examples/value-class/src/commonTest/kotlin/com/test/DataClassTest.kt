package com.test

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class DataClassTest {
    @Test
    fun serialization() {
        val source = DataClass(Point(1.0, 2.0))
        val data = Json.encodeToString(DataClass.serializer(), source)
        val result = Json.decodeFromString(DataClass.serializer(), data)

        assertEquals(source, result)
    }
}
