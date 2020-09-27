import Build_gradle.KfcPlugin.*
import com.gradle.publish.PluginBundleExtension
import com.gradle.publish.PluginConfig

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`

    id("com.gradle.plugin-publish") version "0.12.0"
    id("com.github.turansky.kfc.plugin-publish") version "0.13.0"
}

repositories {
    jcenter()
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

// TODO: remove after Gradle update on Kotlin 1.4
tasks.compileKotlin {
    kotlinOptions.allWarningsAsErrors = false
}

dependencies {
    implementation("nu.studer:java-ordered-properties:1.0.3")

    // TODO: remove version after Gradle update on Kotlin 1.4
    compileOnly(kotlin("gradle-plugin", "1.4.10"))
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
    WEBCOMPONENT("WebComponentPlugin"),
    YFILES("YFilesPlugin"),
    DEV_SERVER("DevServerPlugin"),

    PLUGIN_PUBLISH("PluginPublishPlugin");

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

    plugin(WEBCOMPONENT) {
        displayName = "Kotlin/JS web component plugin"
        description = "Web component adapter for Kotlin/JS"
        tags = tags("webcomponent")
    }

    plugin(YFILES) {
        displayName = "yFiles configuration plugin"
        description = "Provide configuration for yFiles"
        tags = tags("yfiles")
    }

    plugin(DEV_SERVER) {
        displayName = "Development server plugin"
        description = "Testing server for Kotlin/JS"
        tags = tags("dev server", "dev testing")
    }

    plugin(PLUGIN_PUBLISH) {
        displayName = "Plugin publish plugin"
        description = "Provide publish tasks for gradle plugin project"
        tags = tags("gradle", "publish")
    }
}

tasks.wrapper {
    gradleVersion = "6.6.1"
    distributionType = Wrapper.DistributionType.ALL
}
