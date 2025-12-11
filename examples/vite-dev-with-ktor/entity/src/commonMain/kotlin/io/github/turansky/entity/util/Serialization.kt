package io.github.turansky.entity.util

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json

private val defaultJson: Json = Json {
    ignoreUnknownKeys = true
}

fun <T> encodeToString(
    serializer: SerializationStrategy<T>,
    value: T,
): String =
    defaultJson.encodeToString(serializer, value)

fun <T> decodeFromString(
    deserializer: DeserializationStrategy<T>,
    string: String,
): T =
    defaultJson.decodeFromString(deserializer, string)
