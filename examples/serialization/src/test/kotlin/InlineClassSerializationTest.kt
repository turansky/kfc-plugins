import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class InlineClassSerializationTest {
    private lateinit var json: Json

    @BeforeTest
    fun init() {
        json = Json {
            prettyPrint = true
        }
    }

    @Test
    fun inlineKey() {
        assertEquals(Key("42"), json.decodeFromString(Key.serializer(), """ "42" """))
    }
}
