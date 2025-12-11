package io.github.turansky.frontend.components.test

import react.FC
import react.PropsWithChildren
import react.use.useConstant

internal val TestModule: FC<PropsWithChildren> = FC { props ->
    val value = useConstant { "TEST" }

    TestContext(value) {
        +props.children
    }
}
