package com.test.worker

import web.events.EventHandler
import web.messaging.MessageEvent
import web.workers.Worker

fun Worker.addMessageHandler(
    handler: Message.() -> Unit
): () -> Unit {
    val listener: EventHandler<MessageEvent<*, *>> = {
        handler(it.data.unsafeCast<Message>())
    }

    return addEventHandler(MessageEvent.MESSAGE, listener)
}

fun Worker.post(message: Message) {
    postMessage(message)
}
