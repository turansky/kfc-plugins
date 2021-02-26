package com.test.worker

import org.w3c.dom.Worker

fun Worker.onMessage(handler: Message.() -> Unit) {
    onmessage = {
        handler(it.data.unsafeCast<Message>())
    }
}

fun Worker.post(message: Message) {
    postMessage(message)
}
