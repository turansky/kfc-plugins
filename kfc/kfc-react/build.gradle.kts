plugins {
    id("com.github.turansky.kfc.library")
    id("com.github.turansky.kfc.maven-central-publish")
}

val kotlinWrappersVersion = property("kotlin-wrappers.version") as String

dependencies {
    implementation(enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:$kotlinWrappersVersion"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-core")
}
