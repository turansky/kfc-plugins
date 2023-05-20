package webpack.patch

import kotlin.test.Test
import kotlin.test.assertEquals

class ReplaceTest {
    @Test
    fun stringIsReplaced() {
        assertEquals("Frodo", BUILD_NAME)
    }

    @Test
    fun numberIsReplaced() {
        assertEquals(42, BUILD_NUMBER)
        assertEquals(42, ANOTHER_BUILD_NUMBER)
    }
}
