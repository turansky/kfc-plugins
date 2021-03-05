plugins {
    id("com.github.turansky.kfc.library")
}

dependencies {
    implementation(project(":examples:web-worker:entity"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.4.2")
    implementation(ktor("client-js"))
}
