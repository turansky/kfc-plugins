plugins {
    alias(kfc.plugins.library)
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

        jsMain {
            dependsOn(clientCommonMain)
        }

        iosX64Main {
            dependsOn(mobileCommonMain)
        }
    }
}

dependencies {
    commonTestImplementation(libs.kotlin.test)
}
