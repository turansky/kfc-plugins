plugins {
    kotlin("jvm")
    alias(libs.plugins.ktor)
}

application {
    mainClass.set("io.github.turansky.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(libs.server.core)
    implementation(libs.server.netty)
    implementation(libs.server.html.builder)
    implementation(libs.logback)
}
