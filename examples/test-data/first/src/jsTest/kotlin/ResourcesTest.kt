import kotlin.test.Test
import kotlin.test.assertEquals

class ResourcesTest {
    @Test
    fun data() {
        assertEquals(
            expected = "first-data",
            actual = getMyData(),
        )
    }
}
