plugins {
    kotlin("js") apply false
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }
}

tasks.wrapper {
    gradleVersion = "6.8.1"
    distributionType = Wrapper.DistributionType.ALL
}
