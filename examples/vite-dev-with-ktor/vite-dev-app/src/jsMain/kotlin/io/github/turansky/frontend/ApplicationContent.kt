package io.github.turansky.frontend

import emotion.styled.styled
import io.github.turansky.entity.ServerData
import io.github.turansky.entity.util.decodeFromString
import io.github.turansky.frontend.components.Dashboard
import react.FC
import react.dom.html.ReactHTML.div
import react.useEffectOnce
import react.useState
import web.cssom.PlaceContent
import web.cssom.px
import web.cssom.vh
import web.http.fetch
import web.http.text

private suspend fun fetchData(): ServerData =
    decodeFromString(
        ServerData.serializer(),
        fetch("/api/data").text(),
    )

internal val ApplicationContent = FC {
    val (data, setData) = useState<ServerData>()

    useEffectOnce {
        setData(fetchData())
    }

    Container {
        Dashboard {
            value = data
        }
    }
}

private val Container = div.styled {
    height = 95.vh
    padding = 16.px
    placeContent = PlaceContent.center
}
