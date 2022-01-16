package webpack.patch

import kotlin.test.Test
import kotlin.test.assertEquals

class ReplaceTest {
    @Test
    fun valueIsReplaced() {
        assertEquals(BUILD_NUMBER, "||27||")
    }
}
