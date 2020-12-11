package com.test.view

import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement

internal fun div(color: String): HTMLDivElement =
    document.createElement("div")
        .unsafeCast<HTMLDivElement>()
        .apply {
            style.apply {
                width = "100%"
                height = "100%"
                backgroundColor = color
            }
        }
