import Build_gradle.KfcPlugin.*
import com.gradle.publish.PluginBundleExtension
import com.gradle.publish.PluginConfig

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`

    id("com.gradle.plugin-publish")
    id("com.github.turansky.kfc.plugin-publish")
}

dependencies {
    implementation("nu.studer:java-ordered-properties:1.0.4")
    compileOnly(kotlin("gradle-plugin"))
}

// TODO: remove after Gradle update
tasks.compileKotlin {
    kotlinOptions.allWarningsAsErrors = false
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

    // TODO https://github.com/gradle/gradle/issues/19528
    /*
    REACT("ReactPlugin"),
    */
    DEFINITIONS("DefinitionsPlugin"),

    MAVEN_CENTRAL_PUBLISH("MavenCentralPublishPlugin"),
    PLUGIN_PUBLISH("PluginPublishPlugin"),

    LATEST_WEBPACK("LatestWebpackPlugin"),
    LEGACY_UNION("LegacyUnionPlugin"),
    REACT_DATES("ReactDatesPlugin"),

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

    // TODO https://github.com/gradle/gradle/issues/19528
    /*
    plugin(REACT) {
        displayName = "React plugin"
        description = "React support for Kotlin/JS projects"
        tags = tags("react", "lazy")
    }
    */

    plugin(DEFINITIONS) {
        displayName = "Kotlin/JS definitions plugin"
        description = "Kotlin/JS definitions configuration"
        tags = tags("definitions")
    }

    plugin(MAVEN_CENTRAL_PUBLISH) {
        displayName = "Maven central publish plugin"
        description = "Maven central publish configuration"
        tags = tags("maven", "central", "publish")
    }

    plugin(PLUGIN_PUBLISH) {
        displayName = "Plugin publish plugin"
        description = "Provide publish tasks for gradle plugin project"
        tags = tags("publish")
    }

    plugin(LATEST_WEBPACK) {
        displayName = "Latest webpack plugin"
        description = "Configure latest/stable webpack for Kotlin/JS projects"
        tags = tags("webpack")
    }

    plugin(LEGACY_UNION) {
        displayName = "Legacy union plugin"
        description = "Union compatibility plugin for Kotlin/JS legacy compiler"
        tags = tags("webpack")
    }

    plugin(REACT_DATES) {
        displayName = "react-dates plugin"
        description = "React 17+ compatibility fix for react-dates"
        tags = tags("react", "react-dates")
    }
}
