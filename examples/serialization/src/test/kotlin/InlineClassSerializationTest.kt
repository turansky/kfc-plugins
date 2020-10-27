import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class InlineClassSerializationTest {
    private lateinit var json: Json

    @BeforeTest
    fun init() {
        json = Json {
            prettyPrint = false
        }
    }

    @Test
    fun keyDecode() {
        assertEquals(Key("13"), json.decodeFromString(Key.serializer(), """"13""""))
    }

    @Test
    fun keyEncode() {
        assertEquals(""""42"""", json.encodeToString(Key.serializer(), Key("42")))
    }
}
