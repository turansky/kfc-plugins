package io.github.turansky.frontend.components

import io.github.turansky.frontend.components.test.TestModule
import react.FC
import react.Props

internal val CounterModule: FC<Props> = FC {
    TestModule {
        Counter()

        Label()
    }
}
