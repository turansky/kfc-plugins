plugins {
    alias(libs.plugins.kfc.library)
}

dependencies {
    jsMainImplementation(kotlinWrappers.browser)
}
