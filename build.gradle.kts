plugins {
    val kotlinVersion = "1.3.70"
    kotlin("multiplatform") version kotlinVersion apply false
    kotlin("js") version kotlinVersion apply false

    id("com.github.turansky.kfc.webpack") apply false
    id("com.github.turansky.kfc.library") apply false
    id("com.github.turansky.kfc.component") apply false
    id("com.github.turansky.kfc.webcomponent") apply false
    id("com.github.turansky.kfc.yfiles") apply false
}

allprojects {
    repositories {
        jcenter()
    }
}

tasks.wrapper {
    gradleVersion = "6.2.2"
    distributionType = Wrapper.DistributionType.ALL
}
