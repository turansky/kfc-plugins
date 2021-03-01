package com.test.view.io

import com.test.worker.Message
import com.test.worker.addMessageHandler
import com.test.worker.post
import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.Worker

fun main() {
    var count = 1

    val body = document.body!!

    val view = View()
    body.appendChild(view)

    fun log(tag: String, data: Any?) {
        val span = document.createElement("span")
        span.textContent = "$tag: $data"
        view.appendChild(span)
    }

    val worker = Worker("worker-io.js")
    worker.addMessageHandler { log("W[$type]", data) }
    worker.post(Message("Hallo from main!"))

    view.addEventListener("click", {
        count++

        log("C", count)
        worker.post(Message(count))
    })
}

fun View(): HTMLElement {
    val container = document.createElement("div")
        .unsafeCast<HTMLDivElement>()

    container.style.apply {
        width = "100%"
        height = "100%"
        display = "grid"
        backgroundColor = "orange"
    }

    container.textContent = "View IO"

    return container
}
