plugins {
    alias(kfc.plugins.application)
}

dependencies {
    webMainImplementation(kotlinWrappers.browser)
    webMainImplementation(kotlinWrappers.react)
    webMainImplementation(kotlinWrappers.reactDom)
    webMainImplementation(kotlinWrappers.reactUse)
    webMainImplementation(kotlinWrappers.emotion.styled)

    webMainImplementation(projects.examples.viteDevWithKtor.entity)
    webMainImplementation(libs.serialization.json)
}
