package com.test.worker

import web.events.addHandler
import web.workers.Worker

fun Worker.addMessageHandler(
    handler: Message.() -> Unit,
): () -> Unit {
    return messageEvent.addHandler {
        handler(it.data.unsafeCast<Message>())
    }
}

fun Worker.post(message: Message) {
    postMessage(message)
}
