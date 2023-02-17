package com.test.worker.wl

import com.test.worker.Message
import com.test.worker.MessageType
import com.test.worker.addMessageHandler
import com.test.worker.post
import org.w3c.dom.Worker

@JsModule("wl-worker")
external class WLWorker : Worker

private external val self: Worker

private fun main() {
    println("Worker WL start!!!")

    self.addMessageHandler {
        when (type) {
            MessageType.BYTES -> {
                val bytes = data as ByteArray
                self.post(Message("RECEIVE BYTES: ${bytes.size}"))
                self.post(Message("RESULT: ${bytes.decodeToString()}"))
            }

            MessageType.INFO -> {
                self.post(Message("REPLY WL: $data"))
            }
        }
    }

    self.post(Message("Hallo from worker!"))
}
