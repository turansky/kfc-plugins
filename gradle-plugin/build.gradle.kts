plugins {
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.10.1"
    id("org.gradle.kotlin.kotlin-dsl") version "1.3.3"
    id("com.github.turansky.kfc.version") version "0.0.16"

    kotlin("jvm") version "1.3.61"
}

repositories {
    jcenter()
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
            allWarningsAsErrors = true
        }
    }

    jar {
        into("META-INF") {
            from("$projectDir/LICENSE.md")
        }
    }

    wrapper {
        gradleVersion = "6.1.1"
        distributionType = Wrapper.DistributionType.ALL
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(gradleApi())
    implementation("nu.studer:java-ordered-properties:1.0.2")

    compileOnly(kotlin("gradle-plugin"))
}

val REPO_URL = "https://github.com/turansky/kfc-plugins"
val PROJECT_VERSION = project.version.toString()

fun kfc(name: String): String =
    "com.github.turansky.kfc.$name"

fun pluginClass(className: String): String =
    "com.github.turansky.kfc.gradle.plugin.$className"

fun tags(vararg pluginTags: String): List<String> =
    listOf(
        "kotlin",
        "kotlin-js",
        "javascript"
    ) + pluginTags

val ROOT = "root"
val VERSION = "version"
val MAVEN_PUBLISH = "maven-publish"

val LIBRARY = "library"
val COMPONENT = "component"

val PLUGIN_VERSION = "plugin-version"

gradlePlugin {
    plugins {
        create(ROOT) {
            id = kfc(ROOT)
            implementationClass = pluginClass("RootPlugin")
        }

        create(VERSION) {
            id = kfc(VERSION)
            implementationClass = pluginClass("VersionPlugin")
        }

        create(MAVEN_PUBLISH) {
            id = kfc(MAVEN_PUBLISH)
            implementationClass = pluginClass("MavenPublishPlugin")
        }

        create(LIBRARY) {
            id = kfc(LIBRARY)
            implementationClass = pluginClass("LibraryPlugin")
        }

        create(COMPONENT) {
            id = kfc(COMPONENT)
            implementationClass = pluginClass("ComponentPlugin")
        }

        create(PLUGIN_VERSION) {
            id = kfc(PLUGIN_VERSION)
            implementationClass = pluginClass("PluginVersionPlugin")
        }
    }
}

pluginBundle {
    website = REPO_URL
    vcsUrl = REPO_URL

    plugins.getByName(ROOT) {
        displayName = "Root plugin"
        description = "Configure Kotlin/JS plugin in root project"
        tags = tags("root")
        version = PROJECT_VERSION
    }

    plugins.getByName(VERSION) {
        displayName = "Version plugin"
        description = "Provide version tasks in root project"
        tags = tags("version")
        version = PROJECT_VERSION
    }

    plugins.getByName(MAVEN_PUBLISH) {
        displayName = "Maven publish plugin"
        description = "Predefined maven publications for Kotlin"
        tags = tags("maven", "publish")
        version = PROJECT_VERSION
    }

    plugins.getByName(LIBRARY) {
        displayName = "Kotlin library plugin"
        description = "Optimize Kotlin library configuration"
        tags = tags("library")
        version = PROJECT_VERSION
    }

    plugins.getByName(COMPONENT) {
        displayName = "Kotlin/JS component plugin"
        description = "Optimize Kotlin/JS component configuration"
        tags = tags("component")
        version = PROJECT_VERSION
    }

    plugins.getByName(PLUGIN_VERSION) {
        displayName = "Plugin version plugin"
        description = "Provide version tasks for gradle plugin project"
        tags = tags("version")
        version = PROJECT_VERSION
    }
}
