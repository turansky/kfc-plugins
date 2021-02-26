package com.test.worker

import org.w3c.dom.Worker

external val self: Worker

fun main() {
    println("Worker start!!!")

    self.onmessage = {
        when (val data = it.data) {
            is Int -> self.postMessage("Q(${data * data})")
            else -> self.postMessage("REPLY: $data")
        }
    }
    self.postMessage("Hallo from worker!")
}
