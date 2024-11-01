package webpack.patch

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class ReplaceTest {
    // TODO: Migrate on Vitest
    @Ignore
    @Test
    fun stringIsReplaced() {
        assertEquals("Frodo", BUILD_NAME)
    }

    // TODO: Migrate on Vitest
    @Ignore
    @Test
    fun numberIsReplaced() {
        assertEquals("42", BUILD_NUMBER)
        assertEquals("42", ANOTHER_BUILD_NUMBER)
    }
}
