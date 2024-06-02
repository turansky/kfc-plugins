plugins {
    `kotlin-dsl`

    id("com.gradle.plugin-publish")
}

dependencies {
    compileOnly(kotlin("gradle-plugin"))
}

gradlePlugin {
    plugins {
        create("settings") {
            id = "io.github.turansky.kfc.settings"
            displayName = "Settings"
            implementationClass = "io.github.turansky.kfc.gradle.plugin.SettingsPlugin"
        }
    }
}
