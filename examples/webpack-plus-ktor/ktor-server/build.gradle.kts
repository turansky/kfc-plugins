plugins {
    kotlin("jvm")

    application
}

application {
    mainClassName = "io.ktor.server.netty.DevelopmentEngine"
}

dependencies {
    implementation(ktor("server-netty"))
    implementation(ktor("html-builder"))
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation(project(":examples:webpack-plus-ktor:entity"))
}
