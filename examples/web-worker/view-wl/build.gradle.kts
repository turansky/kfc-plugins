plugins {
    id("com.github.turansky.kfc.application")
}

dependencies {
    implementation(project(":examples:web-worker:entity"))
    implementation(project(":examples:web-worker:worker-wl"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.4.2")
    implementation(ktor("client-js"))
}
