plugins {
    alias(libs.plugins.kfc.library)
}

kotlin {
    js()
    iosX64()

    sourceSets {
        val clientCommonMain by creating {
            dependsOn(commonMain.get())
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
    jsTestImplementation(libs.kotlin.test.js)
}
