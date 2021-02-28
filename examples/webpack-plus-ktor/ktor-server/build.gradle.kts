plugins {
    kotlin("jvm")

    application
}

application {
    mainClassName = "io.ktor.server.netty.DevelopmentEngine"
}

dependencies {
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-html-builder:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation(project(":examples:webpack-plus-ktor:entity"))
}
