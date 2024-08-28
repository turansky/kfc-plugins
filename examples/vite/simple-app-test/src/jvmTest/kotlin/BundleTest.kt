import java.io.File
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertNotNull

class BundleTest {
    @Test
    fun `check webpack bundle`() {
        val path = System.getenv("BUNDLE_PATH")
        assertNotNull(path)

        val projectName = "simple-webpack-app"

        val file = File("$path/$projectName/$projectName.js")
        assertContains(file.readText(), "Simple Webpack App")
    }

    @Test
    fun `check vite bundle`() {
        val path = System.getenv("BUNDLE_PATH")
        assertNotNull(path)

        val projectName = "simple-vite-app"

        val file = File("$path/$projectName/$projectName.js")
        assertContains(file.readText(), "Simple Vite App")
    }
}
