package com.test.example.app

import kotlinx.browser.document
import org.w3c.dom.HTMLElement

class App {
    fun whatIsTheMeaningOfLife(): String = "42"

    fun kyky(): List<String> = listOf("ky", "ky")
}

fun RedButton(): HTMLElement =
    document.createElement("button")
        .unsafeCast<HTMLElement>()
