package webpack.patch

import kotlin.test.Test
import kotlin.test.assertEquals

class ReplaceTest {
    @Test
    fun stringIsReplaced() {
        assertEquals(BUILD_NAME, "Frodo")
    }

    @Test
    fun numberIsReplaced() {
        assertEquals(BUILD_NUMBER, 42)
        assertEquals(ANOTHER_BUILD_NUMBER, 42)
    }
}
