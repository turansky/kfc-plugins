plugins {
    alias(kfc.plugins.application)
}

dependencies {
    commonMainImplementation(kotlinWrappers.browser)
    commonMainImplementation(kotlinWrappers.react)
    commonMainImplementation(kotlinWrappers.reactDom)
}
