package com.test.view

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

    val worker = Worker("worker.js")
    worker.onmessage = { log("W", it.data) }
    worker.postMessage("Hallo from !")

    view.addEventListener("click", {
        count++

        log("C", count)
        worker.postMessage(count)
    })
}

fun View(): HTMLElement {
    val container = document.createElement("div")
        .unsafeCast<HTMLDivElement>()

    container.style.apply {
        width = "100%"
        height = "100%"
        display = "grid"
        backgroundColor = "yellow"
    }

    container.textContent = "View"

    return container
}
