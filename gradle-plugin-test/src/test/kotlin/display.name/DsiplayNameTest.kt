package display.name

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@Ignore
class DsiplayNameTest {
    @Test
    fun appName() {
        assertEquals(App.displayName, "App")
    }

    @Test
    fun headerName() {
        assertEquals(Header.displayName, "Header")
    }
}
