plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()

    sourceSets {
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}
