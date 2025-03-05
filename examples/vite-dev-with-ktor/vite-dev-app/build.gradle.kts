plugins {
    alias(kfc.plugins.application)
}

dependencies {
    jsMainImplementation(devNpm("@vitejs/plugin-react", "^4.3.4"))
    jsMainImplementation(kotlinWrappers.browser)
    jsMainImplementation(kotlinWrappers.react)
    jsMainImplementation(kotlinWrappers.reactDom)
}
