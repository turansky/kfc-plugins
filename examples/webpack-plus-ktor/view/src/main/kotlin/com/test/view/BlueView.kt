package com.test.view

import org.w3c.dom.CLOSED
import org.w3c.dom.CustomElement
import org.w3c.dom.ShadowRootInit
import org.w3c.dom.ShadowRootMode
import kotlin.js.Promise

@JsExport
@ExperimentalJsExport
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

    var key: String = "KEY: blue-view"

    var content: Any
        get() = "GET: BlueView content"
        set(value) {
            println("SET: BlueView content")
            println(value)
        }

    fun export(): Promise<String> = Promise.resolve("BLUE-EXPORT")
}
