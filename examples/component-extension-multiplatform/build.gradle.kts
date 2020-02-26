plugins {
    kotlin("multiplatform")
    id("com.github.turansky.kfc.component")
}

kotlin {
    js {
        browser()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

component {
    root = "com.test.example.app"
}
