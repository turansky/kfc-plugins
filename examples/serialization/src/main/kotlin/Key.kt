import kotlinx.serialization.Serializable

@Serializable(with = KeySerializer::class)
@Suppress("INLINE_CLASSES_NOT_SUPPORTED", "EXPERIMENTAL_FEATURE_WARNING")
inline class Key(
    val source: String
)

private object KeySerializer : InlineClassSerializer<Key>(
    "Key", ::Key, { it.source }
)
