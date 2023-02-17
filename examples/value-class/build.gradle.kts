plugins {
    id("io.github.turansky.kfc.library")
    kotlin("plugin.serialization")
}

kotlin {
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
