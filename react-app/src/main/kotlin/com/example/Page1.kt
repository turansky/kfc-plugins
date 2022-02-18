package com.example

import csstype.Color
import kfc.react.import
import kotlinx.js.jso
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.section

val LazyPage1 = react.lazy {
    import(Page1)
}

private val Page1 = FC<Props>("Page1") {
    section {
        style = jso {
            color = Color("#00FF00")
        }

        +"Page1"

        div { +"A1" }
        div { +"A2" }
        div { +"A3" }
        div { +"A4" }
        div { +"A5" }
        div { +"A6" }
        div { +"A7" }
        div { +"A8" }
        div { +"A9" }
        div { +"A0" }
    }
}
