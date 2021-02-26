package com.test.worker

import org.w3c.dom.Worker

external val self: Worker

fun main() {
    println("Worker start!!!")

    self.addEventListener("message", {
        val data = it.asDynamic().data
        println("From main: $data")
    })
    self.postMessage("Hallo from worker!")
}
