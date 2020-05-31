plugins {
    id("com.github.turansky.kfc.multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common")
            }
        }

        jvmMain {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime")
            }
        }

        jsMain {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js")
            }
        }
    }
}
