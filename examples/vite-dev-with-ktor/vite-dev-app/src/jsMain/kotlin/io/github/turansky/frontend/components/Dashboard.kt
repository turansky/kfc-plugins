package io.github.turansky.frontend.components

import emotion.styled.styled
import io.github.turansky.entity.ServerData
import react.FC
import react.PropsWithValue
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.label
import web.cssom.Border
import web.cssom.Color
import web.cssom.Display
import web.cssom.LineStyle.Companion.solid
import web.cssom.px

internal val Dashboard: FC<PropsWithValue<ServerData?>> = FC { props ->
    val value = props.value
        ?: return@FC

    Container {
        label {
            +"Server dir: "
            +value.serverDir
        }
        label {
            +"Static dir: "
            +value.staticDir
        }
        label {
            +"JS: "
            +value.entryFile
        }
    }
}

private val Container = div.styled {
    display = Display.grid

    border = Border(1.px, solid, Color("#FFF"))
    borderRadius = 8.px

    padding = 16.px
    gap = 16.px

    backgroundColor = Color("#EEE")
}
