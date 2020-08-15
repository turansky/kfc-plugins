plugins {
    id("com.github.turansky.kfc.multiplatform")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }

        jvmMain {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }

        jsMain {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
    }
}
