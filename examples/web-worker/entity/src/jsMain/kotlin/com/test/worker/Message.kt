package com.test.worker

external interface Message {
    val type: String
    val data: Any
}

object MessageType {
    val COUNT: String = "count"
    val INFO: String = "info"
    val BYTES: String = "bytes"
}

private fun Message(
    type: String,
    data: Any,
): Message {
    val m = js("({})")
    m.type = type
    m.data = data
    return m
}

fun Message(count: Int): Message =
    Message(MessageType.COUNT, count)

fun Message(message: String): Message =
    Message(MessageType.INFO, message)

fun Message(bytes: ByteArray): Message =
    Message(MessageType.BYTES, bytes)
