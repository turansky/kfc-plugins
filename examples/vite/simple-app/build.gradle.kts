plugins {
    alias(libs.plugins.kfc.application)

    `bundler-test`
}

dependencies {
    jsMainImplementation(libs.wrappers.browser)
    jsTestImplementation(libs.kotlin.test.js)
}
