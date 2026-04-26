plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.javaOrderedProperties)
    implementation(libs.gradlePlugins.kotlin)
}

val copySources by tasks.registering(Copy::class) {
    from("../kfc-gradle-plugin/src/main/kotlin/io/github/turansky/kfc/gradle/plugin") {
        include("GradleExtensions.kt")
        include("GradleProperties.kt")
        include("GradleProperty.kt")
        include("MavenPom.kt")
        include("PluginPublishPlugin.kt")
        include("ProjectVersion.kt")
        include("Tasks.kt")
        include("Version.kt")
    }

    into(temporaryDir)
}

kotlin.sourceSets.main {
    kotlin.srcDir(copySources)
}
