package kfc.test.jso

import kotlin.test.Test
import kotlin.test.assertEquals

class PointInitTest {
    @Test
    fun empty() {
        val point = PointInit()

        assertEquals(null, point.x)
        assertEquals(null, point.y)
    }

    @Test
    fun x() {
        val point = PointInit(x = 4.0)

        assertEquals(4.0, point.x)
        assertEquals(null, point.y)
    }

    @Test
    fun y() {
        val point = PointInit(y = 5.0)

        assertEquals(null, point.x)
        assertEquals(5.0, point.y)
    }

    @Test
    fun xy() {
        val point = PointInit(x = 4.0, y = 5.0)

        assertEquals(4.0, point.x)
        assertEquals(5.0, point.y)
    }
}
