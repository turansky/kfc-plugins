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
    gradleVersion = "7.0"
    distributionType = Wrapper.DistributionType.ALL
}
