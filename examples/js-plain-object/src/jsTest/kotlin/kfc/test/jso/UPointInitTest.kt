package kfc.test.jso

import kotlin.test.Test
import kotlin.test.assertEquals

class UPointInitTest {
    @Test
    fun empty() {
        val point = UPointInit()

        assertEquals(null, point.x)
        assertEquals(null, point.y)
    }

    @Test
    fun x() {
        val point = UPointInit(x = 4u)

        assertEquals(4u, point.x)
        assertEquals(null, point.y)
    }

    @Test
    fun y() {
        val point = UPointInit(y = 5u)

        assertEquals(null, point.x)
        assertEquals(5u, point.y)
    }

    @Test
    fun xy() {
        val point = UPointInit(x = 4u, y = 5u)

        assertEquals(4u, point.x)
        assertEquals(5u, point.y)
    }
}
