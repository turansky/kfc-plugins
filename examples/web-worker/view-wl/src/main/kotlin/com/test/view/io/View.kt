package com.test.view.io

import com.test.worker.Message
import com.test.worker.addMessageHandler
import com.test.worker.post
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.browser.document
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

    val worker = Worker("worker-wl.js")
    worker.addMessageHandler {
        log("W[$type]", data)
    }
    worker.post(Message("START WL!"))

    fun testBytes() {
        GlobalScope.launch {
            val client = HttpClient()
            val bytes = client.get<ByteArray>("https://httpbin.org/get")
            log("DATA", bytes.size)

            worker.post(Message(bytes))
        }
    }

    view.addEventListener("click", {
        testBytes()
    })

    testBytes()
}

fun View(): HTMLElement {
    val container = document.createElement("div")
        .unsafeCast<HTMLDivElement>()

    container.style.apply {
        width = "100%"
        height = "100%"
        display = "grid"
        backgroundColor = "pink"
    }

    container.textContent = "View WL"

    return container
}
