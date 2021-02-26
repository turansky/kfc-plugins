package com.test.view

import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.Worker

fun main() {
    val view = View()
    document.body!!.appendChild(view)

    val worker = Worker("worker.js")
    worker.addEventListener("message", {
        val data = it.asDynamic().data
        view.textContent += "\n\n$data"
    })
    worker.postMessage("Hallo!")
}

fun View(): HTMLElement {
    val container = document.createElement("div")
        .unsafeCast<HTMLDivElement>()

    container.style.apply {
        width = "100%"
        height = "100%"
        backgroundColor = "yellow"
    }

    container.textContent = "View"

    return container
}
