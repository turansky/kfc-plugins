package com.example

import kfc.react.import
import react.FC
import react.Props

val LazyPage2 = react.lazy {
    import(Page2)
}

private val Page2 = FC<Props>("Page2") {
    +"Page2"
}
