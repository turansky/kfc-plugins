package com.test.worker

import web.events.EventTarget
import web.events.addEventHandler
import web.messaging.MessageEvent
import web.workers.Worker

fun Worker.addMessageHandler(
    handler: Message.() -> Unit,
): () -> Unit {
    return addEventHandler(MessageEvent.message<Message, EventTarget>()) {
        handler(it.data)
    }
}

fun Worker.post(message: Message) {
    postMessage(message)
}
