plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")

    application
}

application {
    mainClassName = "io.ktor.server.netty.DevelopmentEngine"
}

val ktorVersion = "1.3.2"
val logbackVersion = "1.2.3"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime")

    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-html-builder:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation(project(":examples:webpack-plus-ktor:entity"))
}
