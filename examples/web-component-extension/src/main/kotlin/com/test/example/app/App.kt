package com.test.example.app

import kotlinx.browser.document
import org.w3c.dom.HTMLElement

class App {
    fun whatIsTheMeaningOfLife(): String = "42"

    fun kyky(): List<String> = listOf("ky", "ky")
}

@JsExport
@ExperimentalJsExport
@Suppress("unused")
class RedButton {
    val __element__: HTMLElement =
        document.createElement("button")
            .unsafeCast<HTMLElement>()
}
