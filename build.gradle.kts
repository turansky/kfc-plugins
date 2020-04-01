plugins {
    val kotlinVersion = "1.3.71"
    kotlin("multiplatform") version kotlinVersion apply false
    kotlin("jvm") version kotlinVersion apply false
    kotlin("js") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false

    id("com.github.turansky.kfc.webpack") apply false
    id("com.github.turansky.kfc.library") apply false
    id("com.github.turansky.kfc.component") apply false
    id("com.github.turansky.kfc.webcomponent") apply false
    id("com.github.turansky.kfc.dev-server") apply false
    id("com.github.turansky.kfc.yfiles") apply false
}

allprojects {
    repositories {
        jcenter()
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
    gradleVersion = "6.3"
    distributionType = Wrapper.DistributionType.ALL
}
