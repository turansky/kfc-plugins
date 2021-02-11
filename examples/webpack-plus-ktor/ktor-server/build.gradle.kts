plugins {
    kotlin("jvm")

    application
}

application {
    mainClassName = "io.ktor.server.netty.DevelopmentEngine"
}

val ktorVersion = "1.5.1"
val logbackVersion = "1.2.3"

dependencies {
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-html-builder:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation(project(":examples:webpack-plus-ktor:entity"))
}
