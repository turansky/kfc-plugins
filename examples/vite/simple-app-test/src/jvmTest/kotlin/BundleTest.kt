import java.io.File
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertNotNull

class BundleTest {
    @Test
    fun `check vite bundle`() {
        checkBundle("simple-vite-app")
    }

    @Test
    fun `check webpack bundle`() {
        checkBundle("simple-webpack-app")
    }

    private fun checkBundle(
        projectName: String,
    ) {
        val path = System.getenv("BUNDLE_PATH")
        assertNotNull(path)

        val file = File("$path/$projectName/$projectName.js")
        assertContains(file.readText(), "Simple App")
    }
}
