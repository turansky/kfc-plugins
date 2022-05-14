plugins {
    id("io.github.turansky.kfc.library")
    id("io.github.turansky.kfc.react")
}

val kotlinWrappersVersion = property("kotlin-wrappers.version") as String

dependencies {
    implementation(enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:$kotlinWrappersVersion"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react")

    testImplementation(kotlin("test-js"))
}

tasks.patchWebpackConfig {
    replace("__BUILD_NAME__", "'Frodo'")
    replace("__BUILD_NUMBER__", "42")
}
