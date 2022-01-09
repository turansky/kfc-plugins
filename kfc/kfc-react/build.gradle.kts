plugins {
    id("com.github.turansky.kfc.library")
    id("com.github.turansky.kfc.maven-central-publish")
}

val kotlinReactVersion = property("kotlin-react.version") as String

dependencies {
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-core:$kotlinReactVersion")
}
