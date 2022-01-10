package com.example

import kfc.react.import
import react.FC
import react.Props

val LazyPage1 = react.lazy {
    import(Page1)
}

private val Page1 = FC<Props>("Page1") {
    +"Page1"
}
