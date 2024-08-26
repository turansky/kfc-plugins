plugins {
    alias(libs.plugins.kfc.library)
}

dependencies {
    jsTestImplementation(libs.kotlin.test.js)
    jsTestImplementation(npm("@test/resources", file("src/jsTest/resources").toURI().toString()))
}
