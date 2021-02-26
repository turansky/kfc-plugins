package com.test.worker

import org.w3c.dom.Worker

external val self: Worker

fun main() {
    println("Worker start!!!")

    self.addMessageHandler {
        when (type) {
            MessageType.COUNT -> {
                val count = data as Int
                self.post(Message(count * count))
            }

            MessageType.INFO -> {
                self.post(Message("REPLY: $data"))
            }
        }
    }

    self.post(Message("Hallo from worker!"))
}
