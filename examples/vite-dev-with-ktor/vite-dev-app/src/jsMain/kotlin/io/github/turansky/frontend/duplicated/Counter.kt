package io.github.turansky.frontend.duplicated

import emotion.styled.styled
import react.FC
import react.Props
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.useCallback
import react.useState
import web.cssom.Border
import web.cssom.Color
import web.cssom.LineStyle.Companion.solid
import web.cssom.px
import web.html.HTMLButtonElement

internal val Counter: FC<Props> = FC {
    val (state, setState) = useState(0)

    val increment: MouseEventHandler<HTMLButtonElement> = useCallback {
        setState { it + 1 }
    }

    Container {
        +"I count to $state"

        button {
            onClick = increment

            +"Increment"
        }
    }
}

private val Container = div.styled {
    border = Border(1.px, solid, Color("#FFF"))
}
