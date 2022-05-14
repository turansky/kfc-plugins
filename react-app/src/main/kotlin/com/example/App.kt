package com.example

import kotlinx.browser.document
import react.Fragment
import react.ReactNode
import react.Suspense
import react.create
import react.dom.client.createRoot

fun main() {
    val container = document.createElement("div")
    document.body!!.appendChild(container)

    val application = Fragment.create {
        Suspense {
            fallback = ReactNode("Loading `Page1`")

            LazyPage1()
        }

        Suspense {
            fallback = ReactNode("Loading `Page2`")

            LazyPage2()
        }
    }

    createRoot(container)
        .render(application)
}
