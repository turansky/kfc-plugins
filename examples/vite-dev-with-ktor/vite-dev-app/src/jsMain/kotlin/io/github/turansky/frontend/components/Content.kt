package io.github.turansky.frontend.components

import emotion.styled.styled
import io.github.turansky.entity.ServerData
import react.FC
import react.PropsWithValue
import react.dom.html.ReactHTML.div
import web.cssom.Display
import web.cssom.PlaceItems
import web.cssom.px

internal val Content: FC<PropsWithValue<ServerData?>> = FC { props ->
    val value = props.value
        ?: return@FC

    Container {
        div {
            +"Server dir: ${value.serverDir}"
        }
        div {
            +"Static dir: ${value.staticDir}"
        }
        div {
            +"JS: ${value.entryFile}"
        }
    }
}

private val Container = div.styled {
    placeItems = PlaceItems.center

    display = Display.grid
    gap = 16.px
}
