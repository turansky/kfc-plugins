// TODO: check, why it's required
plugins {
    kotlin("js") apply false
}

tasks.wrapper {
    gradleVersion = "7.6"
}
