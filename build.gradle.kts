plugins {
    alias(libs.plugins.kotlin.jsPlainObjects) apply false
}

tasks.wrapper {
    gradleVersion = "9.2.1"
}
