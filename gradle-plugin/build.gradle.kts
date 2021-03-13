import Build_gradle.KfcPlugin.*
import com.gradle.publish.PluginBundleExtension
import com.gradle.publish.PluginConfig

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`

    id("com.gradle.plugin-publish") version "0.13.0"
    id("com.github.turansky.kfc.plugin-publish") version "2.9.0"
}

repositories {
    mavenCentral()
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

dependencies {
    implementation("nu.studer:java-ordered-properties:1.0.3")
    compileOnly(kotlin("gradle-plugin"))
}

val REPO_URL = "https://github.com/turansky/kfc-plugins"
val PROJECT_VERSION = project.version.toString()

fun tags(vararg pluginTags: String): List<String> =
    listOf(
        "kotlin",
        "kotlin-js",
        "javascript"
    ) + pluginTags

enum class KfcPlugin(className: String) {
    ROOT("RootPlugin"),
    VERSION("VersionPlugin"),

    NPM_BUILD("NpmBuildPlugin"),
    MAVEN_PUBLISH("MavenPublishPlugin"),

    MULTIPLATFORM("MultiplatformPlugin"),

    WEBPACK("WebpackPlugin"),
    LIBRARY("LibraryPlugin"),
    COMPONENT("ComponentPlugin"),
    APPLICATION("ApplicationPlugin"),
    WORKER("WorkerPlugin"),
    DEV_SERVER("DevServerPlugin"),

    DEFINITIONS("DefinitionsPlugin"),

    PLUGIN_PUBLISH("PluginPublishPlugin"),

    ;

    val pluginName: String = name.toLowerCase().replace("_", "-")

    val id: String = "com.github.turansky.kfc.$pluginName"
    val implementationClass: String = "com.github.turansky.kfc.gradle.plugin.$className"
}

gradlePlugin {
    plugins {
        for (kfcPlugin in values()) {
            create(kfcPlugin.pluginName) {
                id = kfcPlugin.id
                implementationClass = kfcPlugin.implementationClass
            }
        }
    }
}

fun PluginBundleExtension.plugin(
    kfcPlugin: KfcPlugin,
    action: PluginConfig.() -> Unit
) {
    plugins.getByName(kfcPlugin.pluginName) {
        version = PROJECT_VERSION
        action()
    }
}

pluginBundle {
    website = REPO_URL
    vcsUrl = REPO_URL

    plugin(ROOT) {
        displayName = "Root plugin"
        description = "Configure Kotlin/JS plugin in root project"
        tags = tags("root")
    }

    plugin(VERSION) {
        displayName = "Version plugin"
        description = "Provide version tasks in root project"
        tags = tags("version")
    }

    plugin(NPM_BUILD) {
        displayName = "npm build plugin"
        description = "Predefined npm tasks for Kotlin/JS"
        tags = tags("npm")
    }

    plugin(MAVEN_PUBLISH) {
        displayName = "Maven publish plugin"
        description = "Predefined maven publications for Kotlin"
        tags = tags("maven", "publish")
    }

    plugin(MULTIPLATFORM) {
        displayName = "Kotlin multiplatform library plugin"
        description = "Optimize Kotlin multiplatform library configuration"
        tags = tags("multiplatform", "library")
    }

    plugin(WEBPACK) {
        displayName = "Kotlin/JS webpack plugin"
        description = "Webpack configuration for Kotlin/JS"
        tags = tags("webpack", "config")
    }

    plugin(LIBRARY) {
        displayName = "Kotlin library plugin"
        description = "Optimize Kotlin library configuration"
        tags = tags("library")
    }

    plugin(COMPONENT) {
        displayName = "Kotlin/JS component plugin"
        description = "Kotlin/JS component configuration"
        tags = tags("component")
    }

    plugin(APPLICATION) {
        displayName = "Kotlin/JS application plugin"
        description = "Kotlin/JS application configuration"
        tags = tags("application")
    }

    plugin(WORKER) {
        displayName = "Kotlin/JS worker plugin"
        description = "Kotlin/JS worker configuration"
        tags = tags("worker")
    }

    plugin(DEV_SERVER) {
        displayName = "Development server plugin"
        description = "Testing server for Kotlin/JS"
        tags = tags("dev server", "dev testing")
    }

    plugin(DEFINITIONS) {
        displayName = "Kotlin/JS definitions plugin"
        description = "Kotlin/JS definitions configuration"
        tags = tags("definitions")
    }

    plugin(PLUGIN_PUBLISH) {
        displayName = "Plugin publish plugin"
        description = "Provide publish tasks for gradle plugin project"
        tags = tags("gradle", "publish")
    }
}

tasks.wrapper {
    gradleVersion = "6.8.3"
    distributionType = Wrapper.DistributionType.ALL
}
