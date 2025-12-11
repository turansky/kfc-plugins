plugins {
    alias(kfc.plugins.application)
}

dependencies {
    commonMainImplementation(devNpm("@vitejs/plugin-react", "^4.3.4"))
    commonMainImplementation(kotlinWrappers.browser)
    commonMainImplementation(kotlinWrappers.react)
    commonMainImplementation(kotlinWrappers.reactDom)
}
