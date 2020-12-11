package com.test.view

import org.w3c.dom.CustomElement
import org.w3c.dom.OPEN
import org.w3c.dom.ShadowRootInit
import org.w3c.dom.ShadowRootMode

class OrangeView : CustomElement() {
    init {
        val shadow = attachShadow(ShadowRootInit(ShadowRootMode.OPEN))
        shadow.appendChild(div("orange"))
    }
}
