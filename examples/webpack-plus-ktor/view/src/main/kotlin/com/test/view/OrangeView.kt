package com.test.view

import org.w3c.dom.CLOSED
import org.w3c.dom.CustomElement
import org.w3c.dom.ShadowRootInit
import org.w3c.dom.ShadowRootMode

class OrangeView : CustomElement() {
    init {
        val shadow = attachShadow(ShadowRootInit(ShadowRootMode.CLOSED))
        shadow.appendChild(div("orange"))
    }

    override fun connectedCallback() {
        println("OrangeView connected!")
    }

    override fun disconnectedCallback() {
        println("OrangeView disconnected!")
    }
}
