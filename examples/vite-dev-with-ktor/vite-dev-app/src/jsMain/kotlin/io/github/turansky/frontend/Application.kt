package io.github.turansky.frontend

import react.FC
import react.Props

val Application: FC<Props> = FC {
    ApplicationModule {
        ApplicationContent()
    }
}
