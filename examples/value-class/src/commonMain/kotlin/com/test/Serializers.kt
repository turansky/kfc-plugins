package com.test

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class StringSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor
        get() = TODO("not implemented")

    override fun deserialize(decoder: Decoder): String {
        return decoder.decodeString()
    }

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value)
    }
}

class IntSerializer : KSerializer<Int> {
    override val descriptor: SerialDescriptor
        get() = TODO("not implemented")

    override fun deserialize(decoder: Decoder): Int {
        return decoder.decodeInt()
    }

    override fun serialize(encoder: Encoder, value: Int) {
        encoder.encodeInt(value)
    }
}

class DoubleSerializer : KSerializer<Double> {
    override val descriptor: SerialDescriptor
        get() = TODO("not implemented")

    override fun deserialize(decoder: Decoder): Double {
        return decoder.decodeDouble()
    }

    override fun serialize(encoder: Encoder, value: Double) {
        encoder.encodeDouble(value)
    }
}
