package com.test.worker

import web.events.Event
import web.events.addEventHandler
import web.messaging.MESSAGE
import web.messaging.MessageEvent
import web.workers.Worker

fun Worker.addMessageHandler(
    handler: Message.() -> Unit
): () -> Unit {
    val listener: (Event) -> Unit = {
        val message = it.asDynamic().data
        handler(message)
    }

    return addEventHandler(MessageEvent.MESSAGE, listener)
}

fun Worker.post(message: Message) {
    postMessage(message)
}
