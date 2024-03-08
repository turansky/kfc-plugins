package com.test.view.wl

import com.test.worker.Message
import com.test.worker.addMessageHandler
import com.test.worker.post
import com.test.worker.wl.WLWorker
import web.dom.document
import web.events.addEventHandler
import web.html.HTML.div
import web.html.HTML.span
import web.html.HTMLElement
import web.http.fetchAsync
import web.uievents.MouseEvent

private fun main() {
    val view = View()
    document.body.appendChild(view)

    fun log(tag: String, data: Any?) {
        val span = document.createElement(span)
        span.textContent = "$tag: $data"
        view.appendChild(span)
    }

    val worker = WLWorker()
    worker.addMessageHandler {
        log("W[$type]", data)
    }
    worker.post(Message("START WL!"))

    fun testBytes() {
        fetchAsync("https://httpbin.org/get")
            .flatThen { it.text() }
            .then { data ->
                log("DATA", data)
                worker.post(Message(data))
            }
    }

    view.addEventHandler(MouseEvent.click()) {
        testBytes()
    }

    testBytes()
}

fun View(): HTMLElement {
    val container = document.createElement(div)

    container.style.apply {
        width = "100%"
        height = "100%"
        display = "grid"
        backgroundColor = "pink"
    }

    container.textContent = "View WL"

    return container
}
