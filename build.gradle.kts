import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper

plugins {
    kotlin("js") apply false
}

allprojects {
    repositories {
        jcenter()
    }
}

subprojects {
    // TODO: remove after migration on Kotlin 1.4
    plugins.withType<KotlinJsPluginWrapper> {
        dependencies {
            "implementation"(kotlin("stdlib-js"))
        }
    }
}

tasks.wrapper {
    gradleVersion = "6.6"
    distributionType = Wrapper.DistributionType.ALL
}
