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

gradlePlugin {
    plugins {
        create("root") {
            id = "com.github.turansky.kfc.root"
            implementationClass = "com.github.turansky.kfc.gradle.plugin.RootPlugin"
        }
    }
}

pluginBundle {
    val REPO_URL = "https://github.com/turansky/kfc-plugins"

    website = REPO_URL
    vcsUrl = REPO_URL

    plugins.getByName("root") {
        displayName = "Root plugin"
        description = "Configure Kotlin/JS plugin in root project"
        tags = listOf(
            "kotlin",
            "kotlin-js",
            "javascript",
            "root"
        )
        version = project.version.toString()
    }
}
