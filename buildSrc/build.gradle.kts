plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

// TODO: remove after migration on Gradle 7.0
tasks.compileKotlin {
    kotlinOptions.languageVersion = "1.4"
}
