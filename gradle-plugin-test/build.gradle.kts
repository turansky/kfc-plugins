plugins {
    id("io.github.turansky.kfc.library")
}

val kotlinWrappersVersion = property("kotlin-wrappers.version") as String

dependencies {
    jsMainImplementation(enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:$kotlinWrappersVersion"))
    jsMainImplementation("org.jetbrains.kotlin-wrappers:kotlin-react")

    jsTestImplementation(kotlin("test-js"))
}

tasks.patchWebpackConfig {
    replace("__BUILD_NAME__", "'Frodo'")
    replace("__BUILD_NUMBER__", "42")
}
