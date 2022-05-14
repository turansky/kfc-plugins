package display.name

import react.FC
import react.Props

external interface AppProps : Props {
    var name: String?
}

val App = FC<AppProps> {
    Header()
}
