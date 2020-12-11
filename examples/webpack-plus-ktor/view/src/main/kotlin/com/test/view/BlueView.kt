package com.test.view

import kotlinx.browser.document
import org.w3c.dom.*

class BlueView : CustomElement() {
    init {
        val shadow = attachShadow(ShadowRootInit(ShadowRootMode.OPEN))
        shadow.appendChild(div("blue"))
    }
}

private fun div(color: String): HTMLDivElement =
    document.createElement("div")
        .unsafeCast<HTMLDivElement>()
        .apply {
            style.apply {
                width = "100%"
                height = "100%"
                backgroundColor = color
            }
        }
