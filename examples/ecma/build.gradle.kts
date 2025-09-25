plugins {
    alias(kfc.plugins.library)
}

dependencies {
    jsTestImplementation(kotlinWrappers.js)

    jsTestImplementation(libs.kotlin.test)
}
