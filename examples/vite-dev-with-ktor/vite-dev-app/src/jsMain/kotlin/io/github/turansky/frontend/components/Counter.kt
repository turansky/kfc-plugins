package io.github.turansky.frontend.components

import emotion.styled.styled
import io.github.turansky.frontend.components.test.useTest
import react.FC
import react.Props
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.useCallback
import web.cssom.Border
import web.cssom.Color
import web.cssom.LineStyle.Companion.solid
import web.cssom.px
import web.html.HTMLButtonElement

internal val Counter: FC<Props> = FC {
    val test = useTest()
    val increment: MouseEventHandler<HTMLButtonElement> = useCallback {
        console.log("Increment")
    }

    Container {
        button {
            onClick = increment

            +"Increment $test"
        }
    }
}

private val Container = div.styled {
    border = Border(1.px, solid, Color("#FFF"))
}
