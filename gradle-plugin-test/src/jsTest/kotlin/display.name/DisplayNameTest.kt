package display.name

import react.displayName
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@Ignore
class DisplayNameTest {
    @Test
    fun appName() {
        assertEquals(App.displayName, "App")
    }

    @Test
    fun headerName() {
        assertEquals(Header.displayName, "Header")
    }
}
