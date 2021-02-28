package com.test.worker.io

import com.test.worker.Message
import com.test.worker.MessageType
import com.test.worker.addMessageHandler
import com.test.worker.post
import org.w3c.dom.Worker

external val self: Worker

fun main() {
    println("Worker IO start!!!")

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
