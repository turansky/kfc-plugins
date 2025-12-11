package io.github.turansky.frontend.components.test

import react.RequiredContext
import react.createRequiredContext
import react.useRequired

internal val TestContext: RequiredContext<String> =
    createRequiredContext()

internal fun useTest(): String =
    useRequired(TestContext)
