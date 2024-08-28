import java.io.File
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertNotNull

class BundleTest {
    @Test
    fun `check webpack bundle`() {
        val path = System.getenv("BUNDLE_PATH")
        assertNotNull(path)

        val file = File("$path/simple-webpack-app.js")
        assertContains(file.readText(), "Simple Vite App")
    }
}
