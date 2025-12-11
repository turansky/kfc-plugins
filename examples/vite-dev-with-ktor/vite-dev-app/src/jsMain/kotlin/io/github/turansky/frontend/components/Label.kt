package io.github.turansky.frontend.components

import react.FC
import react.memo
import react.useEffect
import react.useState

internal val Label = memo(FC {
    useState(1)

    useEffect { console.log("Label render") }

    +"Hello, React+Kotlin/JS world!"
})
