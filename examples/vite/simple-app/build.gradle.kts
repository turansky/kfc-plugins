plugins {
    alias(libs.plugins.kfc.application)
}

dependencies {
    jsMainImplementation(libs.wrappers.browser)
    jsTestImplementation(libs.kotlin.test.js)
}
