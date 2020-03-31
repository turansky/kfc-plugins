package com.test.view

import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import kotlin.browser.document

@JsName("View")
fun View(): HTMLElement {
    return div().apply {
        style.apply {
            width = "100%"
            height = "100%"
            background = "red"
        }
    }
}

private fun div(): HTMLDivElement =
    document.createElement("div")
        .unsafeCast<HTMLDivElement>()
