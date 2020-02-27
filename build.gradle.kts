plugins {
    kotlin("multiplatform") version "1.3.70-eap-274" apply false
    kotlin("js") version "1.3.70-eap-274" apply false

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
