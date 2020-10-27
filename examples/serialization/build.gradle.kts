import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("js")
    kotlin("plugin.serialization")
}

kotlin.js {
    browser()
}

dependencies {
    testImplementation(kotlin("test-js"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs += "-Xinline-classes"
}
