plugins {
    kotlin("js") apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}

tasks.wrapper {
    gradleVersion = "6.9"
    distributionType = Wrapper.DistributionType.ALL
}
