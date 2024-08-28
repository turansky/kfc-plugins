import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertNotNull

class BundleTest {
    private val path: String? = System.getenv("BUNDLE_PATH")

    @BeforeTest
    fun checkPath() {
        assertNotNull(path)
    }

    @Test
    fun `check vite bundle`() {
        val projectName = "simple-vite-app"

        val file = File("$path/$projectName/$projectName.js")
        assertContains(file.readText(), "Vite")
    }

    @Test
    fun `check webpack bundle`() {
        val projectName = "simple-webpack-app"

        val file = File("$path/$projectName/$projectName.js")
        assertContains(file.readText(), "Webpack")
    }
}
