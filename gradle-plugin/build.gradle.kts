plugins {
    `java-gradle-plugin`
    `kotlin-dsl`

    id("com.gradle.plugin-publish") version "0.11.0"
    id("com.github.turansky.kfc.plugin-publish") version "0.7.6"

    kotlin("jvm") version "1.3.71"
}

repositories {
    jcenter()
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

dependencies {
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

val NPM_BUILD = "npm-build"
val MAVEN_PUBLISH = "maven-publish"

val WEBPACK = "webpack"
val LIBRARY = "library"
val COMPONENT = "component"
val WEB_COMPONENT = "webcomponent"
val YFILES = "yfiles"
val DEV_SERVER = "dev-server"

val PLUGIN_PUBLISH = "plugin-publish"

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

        create(NPM_BUILD) {
            id = kfc(NPM_BUILD)
            implementationClass = pluginClass("NpmBuildPlugin")
        }

        create(MAVEN_PUBLISH) {
            id = kfc(MAVEN_PUBLISH)
            implementationClass = pluginClass("MavenPublishPlugin")
        }

        create(WEBPACK) {
            id = kfc(WEBPACK)
            implementationClass = pluginClass("WebpackPlugin")
        }

        create(LIBRARY) {
            id = kfc(LIBRARY)
            implementationClass = pluginClass("LibraryPlugin")
        }

        create(COMPONENT) {
            id = kfc(COMPONENT)
            implementationClass = pluginClass("ComponentPlugin")
        }

        create(WEB_COMPONENT) {
            id = kfc(WEB_COMPONENT)
            implementationClass = pluginClass("WebComponentPlugin")
        }

        create(YFILES) {
            id = kfc(YFILES)
            implementationClass = pluginClass("YFilesPlugin")
        }

        create(DEV_SERVER) {
            id = kfc(DEV_SERVER)
            implementationClass = pluginClass("DevServerPlugin")
        }

        create(PLUGIN_PUBLISH) {
            id = kfc(PLUGIN_PUBLISH)
            implementationClass = pluginClass("PluginPublishPlugin")
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

    plugins.getByName(NPM_BUILD) {
        displayName = "npm build plugin"
        description = "Predefined npm tasks for Kotlin/JS"
        tags = tags("npm")
        version = PROJECT_VERSION
    }

    plugins.getByName(MAVEN_PUBLISH) {
        displayName = "Maven publish plugin"
        description = "Predefined maven publications for Kotlin"
        tags = tags("maven", "publish")
        version = PROJECT_VERSION
    }

    plugins.getByName(WEBPACK) {
        displayName = "Kotlin/JS webpack plugin"
        description = "Webpack configuration for Kotlin/JS"
        tags = tags("webpack", "config")
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

    plugins.getByName(WEB_COMPONENT) {
        displayName = "Kotlin/JS web component plugin"
        description = "Web component adapter for Kotlin/JS"
        tags = tags("webcomponent")
        version = PROJECT_VERSION
    }

    plugins.getByName(YFILES) {
        displayName = "yFiles configuration plugin"
        description = "Provide configuration for yFiles"
        tags = tags("yfiles")
        version = PROJECT_VERSION
    }

    plugins.getByName(DEV_SERVER) {
        displayName = "Development server plugin"
        description = "Testing server for Kotlin/JS"
        tags = tags("dev server", "dev testing")
        version = PROJECT_VERSION
    }

    plugins.getByName(PLUGIN_PUBLISH) {
        displayName = "Plugin publish plugin"
        description = "Provide publish tasks for gradle plugin project"
        tags = tags("gradle", "publish")
        version = PROJECT_VERSION
    }
}

tasks.wrapper {
    gradleVersion = "6.3"
    distributionType = Wrapper.DistributionType.ALL
}
