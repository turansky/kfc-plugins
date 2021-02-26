package com.test.worker

import org.w3c.dom.Worker
import org.w3c.dom.events.Event

private const val MESSAGE_TYPE: String = "message"

fun Worker.addMessageHandler(
    handler: Message.() -> Unit
): () -> Unit {
    val listener: (Event) -> Unit = {
        val message = it.asDynamic().data
        handler(message)
    }

    addEventListener(MESSAGE_TYPE, listener)
    return { removeEventListener(MESSAGE_TYPE, listener) }
}

fun Worker.post(message: Message) {
    postMessage(message)
}
