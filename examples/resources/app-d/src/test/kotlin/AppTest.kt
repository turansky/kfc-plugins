import com.test.view.Api
import kotlin.test.Test
import kotlin.test.assertEquals

class AppTest {
    @Test
    fun whatIsTheMeaningOfLife() {
        assertEquals(Api.json().asDynamic().app, "test")
    }
}
