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
    gradleVersion = "6.8.3"
    distributionType = Wrapper.DistributionType.ALL
}
