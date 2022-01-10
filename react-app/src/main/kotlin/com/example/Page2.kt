package com.example

import csstype.Color
import kfc.react.import
import kotlinext.js.jso
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.section

val LazyPage2 = react.lazy {
    import(Page2)
}

private val Page2 = FC<Props>("Page2") {
    section {
        style = jso {
            color = Color("#FF0000")
        }

        +"Page2"

        div { +"B1" }
        div { +"B2" }
        div { +"B3" }
        div { +"B4" }
        div { +"B5" }
        div { +"B6" }
        div { +"B7" }
        div { +"B8" }
        div { +"B9" }
        div { +"B0" }
    }
}
