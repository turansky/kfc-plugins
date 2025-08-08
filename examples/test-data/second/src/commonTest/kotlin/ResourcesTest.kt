import kotlin.test.Test
import kotlin.test.assertEquals

class ResourcesTest {
    @Test
    fun data() {
        assertEquals(
            expected = "second-data",
            actual = getMyData(),
        )
    }
}
