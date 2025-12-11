plugins {
    alias(kfc.plugins.application)
}

dependencies {
    commonMainImplementation(devNpm("@vitejs/plugin-react", "^5.0.4"))
    commonMainImplementation(devNpm("@porotkin/vite-plugin-react-kotlinjs", "^1.0.3"))
    webMainImplementation(kotlinWrappers.browser)
    webMainImplementation(kotlinWrappers.react)
    webMainImplementation(kotlinWrappers.reactDom)
    webMainImplementation(kotlinWrappers.reactUse)
    webMainImplementation(kotlinWrappers.emotion.styled)

    webMainImplementation(projects.examples.viteDevWithKtor.entity)
    webMainImplementation(libs.serialization.json)
}
