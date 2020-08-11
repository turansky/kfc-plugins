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

    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlinx" && requested.name.startsWith("kotlinx-serialization-runtime")) {
                useVersion("0.20.0")
            }
        }
    }
}

tasks.wrapper {
    gradleVersion = "6.5.1"
    distributionType = Wrapper.DistributionType.ALL
}
