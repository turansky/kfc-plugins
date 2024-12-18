package kfc.test.jso

import kotlin.test.Test
import kotlin.test.assertEquals

class UPointTest {
    @Test
    fun simple() {
        val point = UPoint(x = 4u, y = 5u)

        assertEquals(4u, point.x)
        assertEquals(5u, point.y)
    }
}
