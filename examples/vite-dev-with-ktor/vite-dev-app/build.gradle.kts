plugins {
    alias(kfc.plugins.application)
}

dependencies {
    commonMainImplementation(devNpm("@vitejs/plugin-react", "^5.1.2"))
    commonMainImplementation(kotlinWrappers.browser)
    commonMainImplementation(kotlinWrappers.react)
    commonMainImplementation(kotlinWrappers.reactDom)
}
