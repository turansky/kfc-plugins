package com.test.view

import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

@JsName("View")
@ExperimentalJsExport
fun View(): HTMLElement {
    val container = div().apply {
        style.apply {
            width = "100%"
            height = "100%"
            backgroundColor = "red"
        }
    }

    sequenceOf(BlueView(), OrangeView())
        .onEach {
            it.style.apply {
                width = "33%"
                height = "100%"
            }
        }
        .forEach { container.appendChild(it) }

    return container
}

private fun div(): HTMLDivElement =
    document.createElement("div")
        .unsafeCast<HTMLDivElement>()
