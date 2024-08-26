plugins {
    alias(libs.plugins.kfc.application)

    `bundle-test-conventions`
}

dependencies {
    jsMainImplementation(libs.wrappers.browser)
    jsTestImplementation(libs.kotlin.test.js)
}
