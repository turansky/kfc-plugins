plugins {
    alias(libs.plugins.kotlin.jsPlainObjects) apply false
}

tasks.wrapper {
    gradleVersion = "9.3.1"
}
