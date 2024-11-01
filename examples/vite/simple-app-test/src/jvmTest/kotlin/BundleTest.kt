import java.io.File
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertNotNull

class BundleTest {
    @Test
    fun `check vite bundle`() {
        checkBundle("simple-vite-app")
    }

    private fun checkBundle(
        projectName: String,
    ) {
        val path = System.getenv("BUNDLE_PATH")
        assertNotNull(path)

        val file = File("$path/$projectName/$projectName.js")
        val content = file.readText()

        assertContains(content, "Simple App")
        assertContains(content, "Frodo Baggins")
        assertContains(content, "212374918234198245123451234")
    }
}
