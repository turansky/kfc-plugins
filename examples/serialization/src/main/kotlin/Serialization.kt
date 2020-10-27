import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal abstract class InlineClassSerializer<T : Any>(
    name: String,
    private val parse: (String) -> T,
    private val stringify: (T) -> String,
) : KSerializer<T> {
    override val descriptor = PrimitiveSerialDescriptor(name, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): T =
        parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: T) {
        encoder.encodeString(stringify(value))
    }
}
