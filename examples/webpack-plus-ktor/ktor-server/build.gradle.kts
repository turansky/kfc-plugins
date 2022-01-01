plugins {
    kotlin("jvm")

    application
}

application {
    mainClass.set("io.ktor.server.netty.DevelopmentEngine")
}

dependencies {
    implementation(ktor("server-netty"))
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation(project(":examples:webpack-plus-ktor:entity"))
}
