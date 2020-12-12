package com.test.view

import org.w3c.dom.CLOSED
import org.w3c.dom.CustomElement
import org.w3c.dom.ShadowRootInit
import org.w3c.dom.ShadowRootMode

class BlueView : CustomElement() {
    init {
        val shadow = attachShadow(ShadowRootInit(ShadowRootMode.CLOSED))
        shadow.appendChild(div("blue"))
    }

    override fun connectedCallback() {
        println("BlueView connected!")
    }

    override fun disconnectedCallback() {
        println("BlueView disconnected!")
    }
}
