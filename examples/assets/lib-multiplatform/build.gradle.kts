plugins {
    id("io.github.turansky.kfc.library")
}

kotlin {
    js()
    iosX64()

    sourceSets {
        val clientCommonMain by creating {
            // do nothing
        }

        val mobileCommonMain by creating {
            dependsOn(clientCommonMain)
        }

        val jsMain by getting {
            dependsOn(clientCommonMain)
        }

        val iosX64Main by getting {
            dependsOn(mobileCommonMain)
        }
    }
}

dependencies {
    jsTestImplementation(kotlin("test-js"))
}
