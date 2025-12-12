plugins {
    alias(kfc.plugins.library)
    alias(libs.plugins.serialization)
}

dependencies {
    commonMainImplementation(libs.serialization.json)
}

kotlin {
    jvm()
}
