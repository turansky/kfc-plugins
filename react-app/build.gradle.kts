plugins {
    id("io.github.turansky.kfc.application")
}

val kotlinWrappersVersion = property("kotlin-wrappers.version") as String

dependencies {
    implementation(enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:$kotlinWrappersVersion"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom")
    implementation("io.github.turansky.kfc:kfc-react")
}
