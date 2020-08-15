package com.test.example.app

import org.w3c.dom.HTMLElement
import kotlinx.browser.document

class App {
    fun whatIsTheMeaningOfLife(): String = "42"

    fun kyky(): List<String> = listOf("ky", "ky")
}

fun RedButton(): HTMLElement =
    document.createElement("button")
        .unsafeCast<HTMLElement>()
