plugins {
    alias(libs.plugins.ktor)
}

application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(libs.server.core)
    implementation(libs.server.netty)
    implementation(libs.logback)
}
