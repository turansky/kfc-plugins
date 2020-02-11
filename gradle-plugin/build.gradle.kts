plugins {
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.10.1"
    id("org.gradle.kotlin.kotlin-dsl") version "1.3.3"

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

    val preparePublish by registering {
        doLast {
            preparePublish()
        }
    }

    val prepareDevelopment by registering {
        doLast {
            prepareDevelopment()
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

    compileOnly(kotlin("gradle-plugin"))
}

val REPO_URL = "https://github.com/turansky/kfc-plugins"
val VERSION = project.version.toString()

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

gradlePlugin {
    plugins {
        create(ROOT) {
            id = kfc(ROOT)
            implementationClass = pluginClass("RootPlugin")
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
        version = VERSION
    }
}
