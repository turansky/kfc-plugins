import kotlin.test.Test
import kotlin.test.assertNotNull

class BundleTest {
    @Test
    fun `check bundle contents`() {
        val bundle = System.getenv("TEST_BUNDLE")
        assertNotNull(bundle)
    }
}
