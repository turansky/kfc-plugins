import java.io.File
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertNotNull

class BundleTest {
    @Test
    fun `check bundle contents`() {
        val bundle = System.getenv("TEST_BUNDLE")
        assertNotNull(bundle)

        val file = File("$bundle/simple-app.js")
        assertContains(file.readText(), "Simple Vite App")
    }
}
