plugins {
    kotlin("js") apply false
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
    }
}

tasks.wrapper {
    gradleVersion = "6.8.3"
    distributionType = Wrapper.DistributionType.ALL
}
