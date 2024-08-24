plugins {
    alias(libs.plugins.kfc.application)

    `test-bundle`
}

dependencies {
    jsMainImplementation(libs.wrappers.browser)
    jsTestImplementation(libs.kotlin.test.js)
}
