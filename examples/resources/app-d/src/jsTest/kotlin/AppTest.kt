import com.test.view.Api
import js.promise.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@Ignore
class AppTest {
    @Test
    fun whatIsTheMeaningOfLife() = runTest {
        val data = Api.json().await()
        assertEquals(data.app, "test")
    }
}
