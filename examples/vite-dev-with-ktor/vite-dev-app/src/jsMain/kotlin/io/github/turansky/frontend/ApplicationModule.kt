package io.github.turansky.frontend

import react.FC
import react.PropsWithChildren
import react.StrictMode

internal val ApplicationModule: FC<PropsWithChildren> = FC { props ->
    StrictMode {
        +props.children
    }
}
