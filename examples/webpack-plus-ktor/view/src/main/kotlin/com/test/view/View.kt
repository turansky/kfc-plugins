package com.test.view

import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

@JsExport
@ExperimentalJsExport
@Suppress("unused")
class View {
    val __element__: HTMLElement =
        div().apply {
            style.apply {
                width = "100%"
                height = "100%"
                backgroundColor = "red"
            }
        }
}

private fun div(): HTMLDivElement =
    document.createElement("div")
        .unsafeCast<HTMLDivElement>()
