package io.github.turansky.frontend

import emotion.styled.styled
import io.github.turansky.entity.ServerData
import io.github.turansky.entity.util.decodeFromString
import io.github.turansky.frontend.components.Content
import io.github.turansky.frontend.components.CounterModule
import io.github.turansky.frontend.duplicated.Counter
import react.FC
import react.dom.html.ReactHTML.div
import react.useEffectOnce
import react.useState
import web.cssom.Border
import web.cssom.Color
import web.cssom.LineStyle.Companion.solid
import web.cssom.PlaceItems
import web.cssom.px
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
        Content {
            value = data
        }

        CounterModule()
        Counter()
    }
}

private val Container = div.styled {
    placeItems = PlaceItems.center
    border = Border(1.px, solid, Color("#FFF"))
}
