plugins {
    alias(libs.plugins.kfc.library)
}

bundlerEnvironment {
    set("BUILD_NAME", "Frodo")
    set("BUILD_NUMBER", "42")
}

dependencies {
    jsTestImplementation(libs.kotlin.testJs)
}
