plugins {
    alias(libs.plugins.kotlin.jsPlainObjects) apply false
}

tasks.wrapper {
    gradleVersion = "8.11.1"
}
