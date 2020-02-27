plugins {
    val kotlinVersion = "1.3.70-eap-274"
    kotlin("multiplatform") version kotlinVersion apply false
    kotlin("js") version kotlinVersion apply false

    id("com.github.turansky.kfc.library") apply false
    id("com.github.turansky.kfc.component") apply false
}

allprojects {
    repositories {
        jcenter()
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

tasks.wrapper {
    gradleVersion = "6.2.1"
    distributionType = Wrapper.DistributionType.ALL
}
