package kfc.test.jso

import kotlin.test.Test
import kotlin.test.assertEquals

class PointTest {
    @Test
    fun simple() {
        val point: Point = Point(x = 4.0, y = 5.0)

        assertEquals(4.0, point.x)
        assertEquals(5.0, point.y)
    }
}
